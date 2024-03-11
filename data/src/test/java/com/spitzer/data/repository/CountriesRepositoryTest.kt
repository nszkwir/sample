package com.spitzer.data.repository

import com.spitzer.common.database.TransactionState
import com.spitzer.data.mapper.CountryModelMapper
import com.spitzer.data.repository.countries.CountriesRepositoryImpl
import com.spitzer.database.dao.CountryDao
import com.spitzer.database.dao.FakeRemoteCountryDao
import com.spitzer.database.model.CountryEntity
import com.spitzer.database.model.FakeRemoteCountryEntity
import com.spitzer.database.testing.TestCountryEntityProvider
import com.spitzer.database.testing.TestFakeRemoteCountryEntityProvider
import com.spitzer.model.data.CountryModel
import com.spitzer.model.testing.TestCountryModelProvider
import com.spitzer.network.CountriesNetworkDatasource
import com.spitzer.network.com.spitzer.network.mapper.CountryNetworkModelMapper
import com.spitzer.network.com.spitzer.network.testing.TestCountryNetworkModelProvider
import com.spitzer.network.model.CountryNetworkModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CountriesRepositoryTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testScope = TestScope(UnconfinedTestDispatcher())
    private val remoteMapper = CountryNetworkModelMapper()
    private val modelMapper = CountryModelMapper()

    private lateinit var subject: CountriesRepository

    // Mock dependencies
    private val remote = mockk<FakeRemoteCountryDao>()
    private val database = mockk<CountryDao>()
    private val restoreDatasource = mockk<CountriesNetworkDatasource>()
    private val networkModelMapper = mockk<CountryNetworkModelMapper>()
    private val countryModelMapper = mockk<CountryModelMapper>()

    private val initialDbFlow = flowOf(
        listOf(
            mockCountryEntity(),
            mockCountryEntity(name = "Uruguay", cca3 = "URU")
        )
    )

    private val initialCountiesMap = modelMapper.mapEntitiesToDataModelMap(emptyList())

    @Before
    fun setup() {
        // Defining mocks behavior
        every { countryModelMapper.mapEntitiesToDataModelMap(any()) } returns initialCountiesMap
        coEvery { database.getCountries() } returns initialDbFlow

        subject = CountriesRepositoryImpl(
            ioDispatcher = Dispatchers.Unconfined,
            restoreDatasource = restoreDatasource,
            remote = remote,
            database = database,
            countryModelMapper = countryModelMapper,
            restoreDatasourceModelMapper = networkModelMapper
        )

    }

    @Test
    fun `repository initialization with empty data from database`() = testScope.runTest {
        val countryEntities = flow { emit(emptyList<CountryEntity>()) }
        val mappedCountries = modelMapper.mapEntitiesToDataModelMap(countryEntities.first())

        // Defining mocks behavior
        every { countryModelMapper.mapEntitiesToDataModelMap(any()) } returns mappedCountries
        coEvery { database.getCountries() } returns countryEntities

        val repository = CountriesRepositoryImpl(
            ioDispatcher = Dispatchers.Unconfined,
            restoreDatasource = restoreDatasource,
            remote = remote,
            database = database,
            countryModelMapper = countryModelMapper,
            restoreDatasourceModelMapper = networkModelMapper
        )

        // Verify interactions
        coVerify { database.getCountries() }
        verify(exactly = 2) { countryModelMapper.mapEntitiesToDataModelMap(any()) }

        // Assertions
        assertEquals(
            expected = emptyMap(),
            actual = repository.countries.first()
        )
    }

    @Test
    fun `repository initialization with data from database`() = testScope.runTest {
        val countryEntities = flow { emit(listOf(mockCountryEntity())) }
        val mappedCountries = modelMapper.mapEntitiesToDataModelMap(countryEntities.first())

        // Defining mocks behavior
        every { countryModelMapper.mapEntitiesToDataModelMap(any()) } returns mappedCountries
        coEvery { database.getCountries() } returns countryEntities

        val repository = CountriesRepositoryImpl(
            ioDispatcher = Dispatchers.Unconfined,
            restoreDatasource = restoreDatasource,
            remote = remote,
            database = database,
            countryModelMapper = countryModelMapper,
            restoreDatasourceModelMapper = networkModelMapper
        )

        // Verify interactions
        coVerify { database.getCountries() }
        verify(exactly = 2) { countryModelMapper.mapEntitiesToDataModelMap(any()) }

        // Assertions
        assertEquals(
            expected = mappedCountries,
            actual = repository.countries.first()
        )
    }

    @Test
    fun `repository initialization with exception from database`() = testScope.runTest {
        val exceptionMessage = "Test exception message"
        val countryEntities = flow<List<CountryEntity>> { throw RuntimeException(exceptionMessage) }

        // Defining mocks behavior
        coEvery { database.getCountries() } returns countryEntities

        val repository = CountriesRepositoryImpl(
            ioDispatcher = Dispatchers.Unconfined,
            restoreDatasource = restoreDatasource,
            remote = remote,
            database = database,
            countryModelMapper = countryModelMapper,
            restoreDatasourceModelMapper = networkModelMapper
        )

        // Verify interactions
        coVerify { database.getCountries() }
        verify(exactly = 1) { countryModelMapper.mapEntitiesToDataModelMap(any()) }

        // Assertions
        assertTrue { repository.countries.first().isEmpty() }
    }

    @Test
    fun `fetchCountriesFromRemote with empty list update local database and countries flow`() =
        testScope.runTest {
            // Assert initial state
            assertEquals(
                actual = subject.countries.first(),
                expected = initialCountiesMap
            )

            // Defining mocks behavior
            coEvery { remote.getCountries() } returns flowOf(emptyList())
            coEvery { database.upsertCountries(any()) } just runs
            coEvery { countryModelMapper.mapFakeRemoteEntityListToEntities(any()) } returns emptyList()
            coEvery { countryModelMapper.mapFakeRemoteEntitiesToDataModelMap(any()) } returns emptyMap()
            // Call the function under test
            subject.fetchCountriesFromRemote()

            // Verify interactions
            verify { remote.getCountries() }
            coVerify { database.upsertCountries(any()) }
            verify { countryModelMapper.mapFakeRemoteEntityListToEntities(any()) }
            verify { countryModelMapper.mapEntitiesToDataModelMap(any()) }
            coVerify(exactly = 0) { networkModelMapper.mapToModel(any()) }
            coVerify(exactly = 0) { restoreDatasource.getCountries() }
            coVerify(exactly = 0) { remote.deleteAllAndInsert(any()) }
            coVerify(exactly = 0) { database.deleteAllCountries() }

            // Assertions
            assertTrue { subject.countries.first().isEmpty() }
        }

    @Test
    fun `fetchCountriesFromRemote update local database and countries flow`() =
        testScope.runTest {
            // Assert initial state
            assertEquals(
                actual = subject.countries.first(),
                expected = initialCountiesMap
            )
            val remoteList = listOf(mockFakeRemoteCountryEntity())
            val dbEntities = modelMapper.mapFakeRemoteEntityListToEntities(remoteList)
            val mappedCountries = modelMapper.mapFakeRemoteEntitiesToDataModelMap(remoteList)

            // Defining mocks behavior
            coEvery { remote.getCountries() } returns flowOf(remoteList)
            coEvery { database.upsertCountries(any()) } just runs
            coEvery { countryModelMapper.mapFakeRemoteEntityListToEntities(any()) } returns dbEntities
            coEvery { countryModelMapper.mapFakeRemoteEntitiesToDataModelMap(any()) } returns mappedCountries

            // Call the function under test
            subject.fetchCountriesFromRemote()

            // Verify interactions
            verify { remote.getCountries() }
            coVerify { database.upsertCountries(any()) }
            verify { countryModelMapper.mapFakeRemoteEntityListToEntities(any()) }
            verify { countryModelMapper.mapEntitiesToDataModelMap(any()) }
            coVerify(exactly = 0) { networkModelMapper.mapToModel(any()) }
            coVerify(exactly = 0) { restoreDatasource.getCountries() }
            coVerify(exactly = 0) { remote.deleteAllAndInsert(any()) }
            coVerify(exactly = 0) { database.deleteAllCountries() }

            // Assertions
            assertEquals(
                actual = subject.countries.first(),
                expected = mappedCountries
            )
        }

    @Test
    fun `fetchCountriesFromRemote exception doesnt update local database and countries flow`() =
        testScope.runTest {
            // Assert initial state
            assertEquals(
                actual = subject.countries.first(),
                expected = initialCountiesMap
            )

            val exceptionMessage = "Test exception message"

            // Defining mocks behavior
            coEvery { remote.getCountries() } returns flow {
                throw RuntimeException(
                    exceptionMessage
                )
            }

            // Call the function under test
            subject.fetchCountriesFromRemote()

            // Verify interactions
            verify { remote.getCountries() }
            coVerify(exactly = 0) { database.upsertCountries(any()) }
            verify(exactly = 0) { countryModelMapper.mapFakeRemoteEntityListToEntities(any()) }
            verify(exactly = 1) { countryModelMapper.mapEntitiesToDataModelMap(any()) }
            coVerify(exactly = 0) { networkModelMapper.mapToModel(any()) }
            coVerify(exactly = 0) { restoreDatasource.getCountries() }
            coVerify(exactly = 0) { remote.deleteAllAndInsert(any()) }
            coVerify(exactly = 0) { database.deleteAllCountries() }

            // Assertions
            assertEquals(
                actual = subject.countries.first(),
                expected = initialCountiesMap
            )
        }

    @Test
    fun `fetchCountriesFromRemote succeed, local database fails and countries flow is not updated`() =
        testScope.runTest {
            // Assert initial state
            assertEquals(
                actual = subject.countries.first(),
                expected = initialCountiesMap
            )

            val exceptionMessage = "Test exception message"
            val remoteList = listOf(mockFakeRemoteCountryEntity())
            val dbEntities = modelMapper.mapFakeRemoteEntityListToEntities(remoteList)

            // Defining mocks behavior
            coEvery { remote.getCountries() } returns flowOf(remoteList)
            coEvery { database.upsertCountries(any()) } coAnswers {
                throw RuntimeException(
                    exceptionMessage
                )
            }
            coEvery { countryModelMapper.mapFakeRemoteEntityListToEntities(any()) } returns dbEntities

            // Call the function under test
            subject.fetchCountriesFromRemote()

            // Verify interactions
            verify { remote.getCountries() }
            coVerify { database.upsertCountries(any()) }
            verify { countryModelMapper.mapFakeRemoteEntityListToEntities(any()) }
            verify(exactly = 1) { countryModelMapper.mapEntitiesToDataModelMap(any()) }
            coVerify(exactly = 0) { networkModelMapper.mapToModel(any()) }
            coVerify(exactly = 0) { restoreDatasource.getCountries() }
            coVerify(exactly = 0) { remote.deleteAllAndInsert(any()) }
            coVerify(exactly = 0) { database.deleteAllCountries() }

            // Assertions
            assertEquals(
                actual = subject.countries.first(),
                expected = initialCountiesMap
            )
        }

    @Test
    fun `updateCountry with a new country should update remote, local database, and countries flow`() =
        testScope.runTest {
            // Assert initial state
            assertEquals(
                actual = subject.countries.first(),
                expected = initialCountiesMap
            )

            // Fetching data from remote
            val remoteList = listOf(mockFakeRemoteCountryEntity())
            val dbEntities = modelMapper.mapFakeRemoteEntityListToEntities(remoteList)
            val mappedCountries = modelMapper.mapFakeRemoteEntitiesToDataModelMap(remoteList)

            // Defining mocks behavior
            coEvery { remote.getCountries() } returns flowOf(remoteList)
            coEvery { database.upsertCountries(any()) } just runs
            coEvery { countryModelMapper.mapFakeRemoteEntityListToEntities(any()) } returns dbEntities
            coEvery { countryModelMapper.mapFakeRemoteEntitiesToDataModelMap(any()) } returns mappedCountries

            // Call the function under test
            subject.fetchCountriesFromRemote()

            val country = mockCountryModel(cca3 = "BRA", name = "Brasil")
            val remoteCountry = modelMapper.mapDataModelToFakeRemoteEntity(country)
            val dbEntity = modelMapper.mapDataModelToEntity(country)
            val updatedCountriesMap = mappedCountries.toMutableMap().apply {
                put(country.cca3, country)
            }

            // Defining mocks behavior
            coEvery { remote.upsertCountry(any()) } just runs
            coEvery { database.upsertCountry(any()) } just runs
            coEvery { countryModelMapper.mapDataModelToFakeRemoteEntity(any()) } returns remoteCountry
            coEvery { countryModelMapper.mapDataModelToEntity(any()) } returns dbEntity

            // Call the function under test
            subject.updateCountry(country)

            // Verify interactions
            coVerify { remote.upsertCountry(remoteCountry) }
            coVerify { database.upsertCountry(dbEntity) }
            verify { countryModelMapper.mapFakeRemoteEntityListToEntities(any()) }
            verify { countryModelMapper.mapEntitiesToDataModelMap(any()) }
            verify { countryModelMapper.mapDataModelToFakeRemoteEntity(any()) }
            verify { countryModelMapper.mapDataModelToEntity(any()) }
            coVerify(exactly = 0) { networkModelMapper.mapToModel(any()) }
            coVerify(exactly = 0) { restoreDatasource.getCountries() }
            coVerify(exactly = 0) { remote.deleteAllAndInsert(any()) }
            coVerify(exactly = 0) { database.deleteAllCountries() }

            // Assertions
            assertEquals(subject.countries.first(), updatedCountriesMap)
        }

    @Test
    fun `updateCountry should update remote, local database, and countries flow`() =
        testScope.runTest {
            // Assert initial state
            assertEquals(
                actual = subject.countries.first(),
                expected = initialCountiesMap
            )

            // Fetching data from remote
            val remoteList = listOf(mockFakeRemoteCountryEntity())
            val dbEntities = modelMapper.mapFakeRemoteEntityListToEntities(remoteList)
            val mappedCountries = modelMapper.mapFakeRemoteEntitiesToDataModelMap(remoteList)

            // Defining mocks behavior
            coEvery { remote.getCountries() } returns flowOf(remoteList)
            coEvery { database.upsertCountries(any()) } just runs
            coEvery { countryModelMapper.mapFakeRemoteEntityListToEntities(any()) } returns dbEntities
            coEvery { countryModelMapper.mapFakeRemoteEntitiesToDataModelMap(any()) } returns mappedCountries

            // Call the function under test
            subject.fetchCountriesFromRemote()

            val country = mappedCountries.values.first().copy(tags = "New tags!")
            val remoteCountry = modelMapper.mapDataModelToFakeRemoteEntity(country)
            val dbEntity = modelMapper.mapDataModelToEntity(country)
            val updatedCountriesMap = mappedCountries.toMutableMap().apply {
                put(country.cca3, country)
            }

            // Defining mocks behavior
            coEvery { remote.upsertCountry(any()) } just runs
            coEvery { database.upsertCountry(any()) } just runs
            coEvery { countryModelMapper.mapDataModelToFakeRemoteEntity(any()) } returns remoteCountry
            coEvery { countryModelMapper.mapDataModelToEntity(any()) } returns dbEntity

            // Call the function under test
            subject.updateCountry(country)

            // Verify interactions
            coVerify { remote.upsertCountry(remoteCountry) }
            coVerify { database.upsertCountry(dbEntity) }
            verify { countryModelMapper.mapFakeRemoteEntityListToEntities(any()) }
            verify { countryModelMapper.mapEntitiesToDataModelMap(any()) }
            verify { countryModelMapper.mapDataModelToFakeRemoteEntity(any()) }
            verify { countryModelMapper.mapDataModelToEntity(any()) }
            coVerify(exactly = 0) { networkModelMapper.mapToModel(any()) }
            coVerify(exactly = 0) { restoreDatasource.getCountries() }
            coVerify(exactly = 0) { remote.deleteAllAndInsert(any()) }
            coVerify(exactly = 0) { database.deleteAllCountries() }

            // Assertions
            assertEquals(subject.countries.first(), updatedCountriesMap)
        }

    @Test
    fun `updateCountry fails remote, doesnt update local database and countries flow`() =
        testScope.runTest {
            // Assert initial state
            assertEquals(
                actual = subject.countries.first(),
                expected = initialCountiesMap
            )

            val country = mockCountryModel()
            val remoteCountry = modelMapper.mapDataModelToFakeRemoteEntity(country)
            val dbEntity = modelMapper.mapDataModelToEntity(country)
            val exceptionMessage = "Test exception message"

            // Defining mocks behavior
            coEvery { remote.upsertCountry(any()) } coAnswers {
                throw RuntimeException(
                    exceptionMessage
                )
            }
            coEvery { countryModelMapper.mapDataModelToFakeRemoteEntity(any()) } returns remoteCountry

            // Call the function under test
            subject.updateCountry(country)

            // Verify interactions
            coVerify { remote.upsertCountry(remoteCountry) }
            coVerify(exactly = 0) { database.upsertCountry(dbEntity) }
            verify(exactly = 0) { countryModelMapper.mapFakeRemoteEntityListToEntities(any()) }
            verify { countryModelMapper.mapEntitiesToDataModelMap(any()) }
            verify { countryModelMapper.mapDataModelToFakeRemoteEntity(any()) }
            verify(exactly = 0) { countryModelMapper.mapDataModelToEntity(any()) }
            coVerify(exactly = 0) { networkModelMapper.mapToModel(any()) }
            coVerify(exactly = 0) { restoreDatasource.getCountries() }
            coVerify(exactly = 0) { remote.deleteAllAndInsert(any()) }
            coVerify(exactly = 0) { database.deleteAllCountries() }

            // Assertions
            assertEquals(subject.countries.first(), initialCountiesMap)
        }

    @Test
    fun `updateCountry fails local database doesnt update countries flow`() =
        testScope.runTest {
            // Assert initial state
            assertEquals(
                actual = subject.countries.first(),
                expected = initialCountiesMap
            )

            val country = mockCountryModel()
            val remoteCountry = modelMapper.mapDataModelToFakeRemoteEntity(country)
            val dbEntity = modelMapper.mapDataModelToEntity(country)
            val exceptionMessage = "Test exception message"

            // Defining mocks behavior
            coEvery { remote.upsertCountry(any()) } just runs
            coEvery { database.upsertCountry(any()) } coAnswers {
                throw RuntimeException(
                    exceptionMessage
                )
            }
            coEvery { countryModelMapper.mapDataModelToFakeRemoteEntity(any()) } returns remoteCountry
            coEvery { countryModelMapper.mapDataModelToEntity(any()) } returns dbEntity

            // Call the function under test
            subject.updateCountry(country)

            // Verify interactions
            coVerify { remote.upsertCountry(remoteCountry) }
            coVerify { database.upsertCountry(dbEntity) }
            verify(exactly = 0) { countryModelMapper.mapFakeRemoteEntityListToEntities(any()) }
            verify { countryModelMapper.mapEntitiesToDataModelMap(any()) }
            verify { countryModelMapper.mapDataModelToFakeRemoteEntity(any()) }
            verify { countryModelMapper.mapDataModelToEntity(any()) }
            coVerify(exactly = 0) { networkModelMapper.mapToModel(any()) }
            coVerify(exactly = 0) { restoreDatasource.getCountries() }
            coVerify(exactly = 0) { remote.deleteAllAndInsert(any()) }
            coVerify(exactly = 0) { database.deleteAllCountries() }

            // Assertions
            assertEquals(subject.countries.first(), initialCountiesMap)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `restoreCountries should send TransactionState SUCCESS when successful`() =
        runTest {
            // Assert initial state
            assertEquals(
                actual = subject.countries.first(),
                expected = initialCountiesMap
            )

            val networkModelList = listOf(mockCountryNetworkModel())
            val mappedModels =
                remoteMapper.mapNetworkListToDataModelList(networkModelList)
            val fakeRemoteEntities =
                modelMapper.mapDataModelListToFakeRemoteEntities(mappedModels)

            // Defining mocks behavior
            coEvery { restoreDatasource.getCountries() } returns networkModelList
            every { networkModelMapper.mapNetworkListToDataModelList(any()) } returns mappedModels
            every { countryModelMapper.mapDataModelListToFakeRemoteEntities(any()) } returns fakeRemoteEntities
            coEvery { remote.deleteAllAndInsert(any()) } just runs
            coEvery { database.deleteAllCountries() } just runs


            // Call the function under test
            val collectJob = launch(UnconfinedTestDispatcher()) {
                val list = subject.restoreCountries().toList()
                assertEquals(actual = list[0], expected = TransactionState.IN_PROGRESS)
                assertEquals(actual = list[1], expected = TransactionState.SUCCESS)
                assertEquals(actual = list[2], expected = TransactionState.IDLE)
            }

            // Verify interactions
            coVerify { restoreDatasource.getCountries() }
            coVerify { remote.deleteAllAndInsert(any()) }
            coVerify { database.deleteAllCountries() }
            coVerify { networkModelMapper.mapNetworkListToDataModelList(any()) }
            coVerify { countryModelMapper.mapDataModelListToFakeRemoteEntities(any()) }

            collectJob.cancel()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `restoreCountries should send TransactionState ERROR when restoreDataSource fails`() =
        runTest {
            // Assert initial state
            assertEquals(
                actual = subject.countries.first(),
                expected = initialCountiesMap
            )

            val networkModelList = listOf(mockCountryNetworkModel())
            val mappedModels =
                remoteMapper.mapNetworkListToDataModelList(networkModelList)
            val fakeRemoteEntities =
                modelMapper.mapDataModelListToFakeRemoteEntities(mappedModels)
            val exceptionMessage = "Test exception message"

            // Defining mocks behavior
            coEvery { restoreDatasource.getCountries() } coAnswers {
                throw RuntimeException(
                    exceptionMessage
                )
            }
            //every { mockkCountryNetworkModelMapper.mapNetworkListToDataModelList(any()) } returns mappedModels
            //every { mockkCountryModelMapper.mapDataModelListToFakeRemoteEntities(any()) } returns fakeRemoteEntities
            //coEvery { mockkRemote.deleteAllAndInsert(any()) } just runs
            //coEvery { mockkDatabase.deleteAllCountries() } just runs


            // Call the function under test
            val collectJob = launch(UnconfinedTestDispatcher()) {
                val list = subject.restoreCountries().toList()
                assertEquals(actual = list[0], expected = TransactionState.IN_PROGRESS)
                assertEquals(actual = list[1], expected = TransactionState.ERROR)
                assertEquals(actual = list[2], expected = TransactionState.IDLE)
            }

            // Verify interactions
            coVerify { restoreDatasource.getCountries() }
            coVerify(exactly = 0) { remote.deleteAllAndInsert(any()) }
            coVerify(exactly = 0) { database.deleteAllCountries() }
            coVerify(exactly = 0) { networkModelMapper.mapNetworkListToDataModelList(any()) }
            coVerify(exactly = 0) { countryModelMapper.mapDataModelListToFakeRemoteEntities(any()) }

            collectJob.cancel()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `restoreCountries should send TransactionState ERROR when remote fails`() =
        runTest {
            // Assert initial state
            assertEquals(
                actual = subject.countries.first(),
                expected = initialCountiesMap
            )

            val networkModelList = listOf(mockCountryNetworkModel())
            val mappedModels =
                remoteMapper.mapNetworkListToDataModelList(networkModelList)
            val fakeRemoteEntities =
                modelMapper.mapDataModelListToFakeRemoteEntities(mappedModels)
            val exceptionMessage = "Test exception message"

            // Defining mocks behavior
            coEvery { restoreDatasource.getCountries() } returns networkModelList
            every { networkModelMapper.mapNetworkListToDataModelList(any()) } returns mappedModels
            every { countryModelMapper.mapDataModelListToFakeRemoteEntities(any()) } returns fakeRemoteEntities
            coEvery { remote.deleteAllAndInsert(any()) } coAnswers {
                throw RuntimeException(
                    exceptionMessage
                )
            }

            // Call the function under test
            val collectJob = launch(UnconfinedTestDispatcher()) {
                val list = subject.restoreCountries().toList()
                assertEquals(actual = list[0], expected = TransactionState.IN_PROGRESS)
                assertEquals(actual = list[1], expected = TransactionState.ERROR)
                assertEquals(actual = list[2], expected = TransactionState.IDLE)
            }

            // Verify interactions
            coVerify { restoreDatasource.getCountries() }
            coVerify { remote.deleteAllAndInsert(any()) }
            coVerify(exactly = 0) { database.deleteAllCountries() }
            coVerify { networkModelMapper.mapNetworkListToDataModelList(any()) }
            coVerify { countryModelMapper.mapDataModelListToFakeRemoteEntities(any()) }

            collectJob.cancel()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `restoreCountries should send TransactionState ERRPR when database fails`() =
        runTest {
            // Assert initial state
            assertEquals(
                actual = subject.countries.first(),
                expected = initialCountiesMap
            )

            val networkModelList = listOf(mockCountryNetworkModel())
            val mappedModels =
                remoteMapper.mapNetworkListToDataModelList(networkModelList)
            val fakeRemoteEntities =
                modelMapper.mapDataModelListToFakeRemoteEntities(mappedModels)
            val exceptionMessage = "Test exception message"

            // Defining mocks behavior
            coEvery { restoreDatasource.getCountries() } returns networkModelList
            every { networkModelMapper.mapNetworkListToDataModelList(any()) } returns mappedModels
            every { countryModelMapper.mapDataModelListToFakeRemoteEntities(any()) } returns fakeRemoteEntities
            coEvery { remote.deleteAllAndInsert(any()) } just runs
            coEvery { database.deleteAllCountries() } coAnswers {
                throw RuntimeException(
                    exceptionMessage
                )
            }


            // Call the function under test
            val collectJob = launch(UnconfinedTestDispatcher()) {
                val list = subject.restoreCountries().toList()
                assertEquals(actual = list[0], expected = TransactionState.IN_PROGRESS)
                assertEquals(actual = list[1], expected = TransactionState.ERROR)
                assertEquals(actual = list[2], expected = TransactionState.IDLE)
            }

            // Verify interactions
            coVerify { restoreDatasource.getCountries() }
            coVerify { remote.deleteAllAndInsert(any()) }
            coVerify { database.deleteAllCountries() }
            coVerify { networkModelMapper.mapNetworkListToDataModelList(any()) }
            coVerify { countryModelMapper.mapDataModelListToFakeRemoteEntities(any()) }

            collectJob.cancel()
        }

    private fun mockCountryModel(cca3: String? = null, name: String? = null): CountryModel {
        // Mock your CountryModel as needed
        return TestCountryModelProvider.getTestCountryModel(cca3 = cca3, name = name)
    }

    private fun mockCountryEntity(cca3: String? = null, name: String? = null): CountryEntity {
        // Mock your RestoreCountryEntity as needed
        return TestCountryEntityProvider.getCountryEntity(cca3 = cca3, name = name)
    }

    private fun mockFakeRemoteCountryEntity(
        cca3: String? = null,
        name: String? = null
    ): FakeRemoteCountryEntity {
        // Mock your RestoreCountryEntity as needed
        return TestFakeRemoteCountryEntityProvider.getFakeRemoteCountryEntity(
            cca3 = cca3,
            name = name
        )
    }

    private fun mockCountryNetworkModel(
        cca3: String? = null,
        name: String? = null
    ): CountryNetworkModel {
        // Mock your RestoreCountryEntity as needed
        return TestCountryNetworkModelProvider.getCountryNetworkModel(cca3 = cca3, name = name)
    }
}
