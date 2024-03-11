package com.spitzer.data.repository

import com.spitzer.data.mapper.LanguageModelMapper
import com.spitzer.data.repository.languages.LanguagesRepositoryImpl
import com.spitzer.network.com.spitzer.network.LanguagesNetworkDatasource
import com.spitzer.network.com.spitzer.network.model.ISOLanguageNetworkModel
import com.spitzer.network.com.spitzer.network.testing.TestISOLanguageNetworkModelProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class LanguagesRepositoryTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testScope = TestScope(UnconfinedTestDispatcher())
    private val languagesMapper = LanguageModelMapper()

    private lateinit var subject: LanguagesRepository

    // Mock dependencies
    private val dataSource = mockk<LanguagesNetworkDatasource>()
    private val mapper = mockk<LanguageModelMapper>()
    private val exceptionMessage = "Test exception message"

    @Test
    fun `repository initialization with empty data from database`() = testScope.runTest {
        val languages = emptyList<ISOLanguageNetworkModel>()
        val mappedLanguages = languagesMapper.mapNetworkModelListToDataModels(languages)

        // Defining mocks behavior
        every { mapper.mapNetworkModelListToDataModels(any()) } returns mappedLanguages
        coEvery { dataSource.getLanguages() } returns languages

        val repository = LanguagesRepositoryImpl(
            ioDispatcher = Dispatchers.Unconfined,
            datasource = dataSource,
            mapper = mapper
        )

        // Verify interactions
        coVerify { dataSource.getLanguages() }
        verify { mapper.mapNetworkModelListToDataModels(any()) }

        // Assertions
        assertEquals(
            expected = emptyList(),
            actual = repository.languages.first()
        )
    }

    @Test
    fun `repository initialization data from database`() = testScope.runTest {
        val languages = listOf(
            TestISOLanguageNetworkModelProvider.getNetworkModel()
        )

        val mappedLanguages = languagesMapper.mapNetworkModelListToDataModels(languages)

        // Defining mocks behavior
        every { mapper.mapNetworkModelListToDataModels(any()) } returns mappedLanguages
        coEvery { dataSource.getLanguages() } returns languages

        val repository = LanguagesRepositoryImpl(
            ioDispatcher = Dispatchers.Unconfined,
            datasource = dataSource,
            mapper = mapper
        )

        // Verify interactions
        coVerify { dataSource.getLanguages() }
        verify { mapper.mapNetworkModelListToDataModels(any()) }

        // Assertions
        assertEquals(
            expected = mappedLanguages,
            actual = repository.languages.first()
        )
    }

    @Test
    fun `repository initialization fails`() = testScope.runTest {
        val languages = listOf(
            TestISOLanguageNetworkModelProvider.getNetworkModel()
        )

        val mappedLanguages = languagesMapper.mapNetworkModelListToDataModels(languages)

        // Defining mocks behavior
        every { mapper.mapNetworkModelListToDataModels(any()) } returns mappedLanguages
        coEvery { dataSource.getLanguages() } coAnswers {
            throw RuntimeException(
                exceptionMessage
            )
        }

        val repository = LanguagesRepositoryImpl(
            ioDispatcher = Dispatchers.Unconfined,
            datasource = dataSource,
            mapper = mapper
        )

        // Verify interactions
        coVerify { dataSource.getLanguages() }
        verify(exactly = 0) { mapper.mapNetworkModelListToDataModels(any()) }

        // Assertions
        assertEquals(
            expected = emptyList(),
            actual = repository.languages.first()
        )
    }

    @Test
    fun `getting languages transformed to map collection`() = testScope.runTest {
        val languages = listOf(
            TestISOLanguageNetworkModelProvider.getNetworkModel()
        )
        val mappedLanguages = languagesMapper.mapNetworkModelListToDataModels(languages)

        // Defining mocks behavior
        every { mapper.mapNetworkModelListToDataModels(any()) } returns mappedLanguages
        coEvery { dataSource.getLanguages() } returns languages

        val repository = LanguagesRepositoryImpl(
            ioDispatcher = Dispatchers.Unconfined,
            datasource = dataSource,
            mapper = mapper
        )

        val languagesMap = repository.getLanguages()

        // Verify interactions
        coVerify { dataSource.getLanguages() }
        verify { mapper.mapNetworkModelListToDataModels(any()) }

        // Assertions
        assertEquals(
            expected = mappedLanguages,
            actual = repository.languages.first()
        )
        assertEquals(
            expected = mappedLanguages.associateBy({ it.code3 }, { it }),
            actual = languagesMap
        )
    }

    @Test
    fun `fetching languages from remote and updating flow`() = testScope.runTest {
        val languages = listOf(
            TestISOLanguageNetworkModelProvider.getNetworkModel()
        )
        val mappedLanguages = languagesMapper.mapNetworkModelListToDataModels(languages)

        // Defining mocks behavior
        coEvery { dataSource.getLanguages() } returns languages
        every { mapper.mapNetworkModelListToDataModels(languages) } returns mappedLanguages

        val repository = LanguagesRepositoryImpl(
            ioDispatcher = Dispatchers.Unconfined,
            datasource = dataSource,
            mapper = mapper
        )

        // Assertions
        assertEquals(
            expected = mappedLanguages,
            actual = repository.languages.first()
        )

        // updating data
        val updatedLanguages = listOf(
            TestISOLanguageNetworkModelProvider.getNetworkModel(),
            TestISOLanguageNetworkModelProvider.getNetworkModel(code2 = "xx", code3 = "xxx", language = "XXXXXXXXXX")

        )
        val updatedMappedLanguages =
            languagesMapper.mapNetworkModelListToDataModels(updatedLanguages)

        // Defining mocks behavior
        coEvery { dataSource.getLanguages() } returns updatedLanguages
        every { mapper.mapNetworkModelListToDataModels(updatedLanguages) } returns updatedMappedLanguages

        repository.fetchLanguagesFromRemote()

        // Verify interactions
        coVerify { dataSource.getLanguages() }
        verify { mapper.mapNetworkModelListToDataModels(any()) }

        // Assertions
        assertEquals(
            expected = updatedMappedLanguages,
            actual = repository.languages.first()
        )
    }

}
