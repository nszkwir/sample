package com.spitzer.ui.feature.countries

import app.cash.turbine.test
import com.spitzer.common.testing.di.MainDispatcherRule
import com.spitzer.data.repository.CountriesRepository
import com.spitzer.model.testing.TestCountryModelProvider
import com.spitzer.ui.feature.FakeTestCountriesRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class CountriesViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    // Mocks
    private val countriesRepository: CountriesRepository = FakeTestCountriesRepository()

    private lateinit var viewModel: CountriesViewModel

    private val exceptionMessage = "Test exception message"

    @Before
    fun setup() {
        viewModel = CountriesViewModel(countriesRepository)
    }

    @Test
    fun `initializing viewModel sets uiState as Loading`() = runTest {
        assertEquals(actual = viewModel.countriesState.value, expected = CountriesUiState.Loading)
    }

    @Test
    fun `after initializing viewModel, the uiState is set to Success`() = runTest {
        val countriesMap = listOf(
            TestCountryModelProvider.getTestCountryModel()
        ).associateBy({ it.cca3 }, { it })

        viewModel.countriesState.test {
            assertEquals(expected = CountriesUiState.Loading, actual = awaitItem())
            (countriesRepository as FakeTestCountriesRepository).updateCountriesMap(countriesMap)
            assertEquals(expected = CountriesUiState.Success(countriesMap), actual = awaitItem())
        }
    }

    @Test
    fun `after initializing viewModel, the uiState is set to Error`() = runTest {
        (countriesRepository as FakeTestCountriesRepository).emitException(exceptionMessage)
        viewModel = CountriesViewModel(countriesRepository)

        viewModel.countriesState.test {
            assertEquals(expected = CountriesUiState.Error, actual = awaitItem())
        }
    }

    @Test
    fun `refreshCountryList should update countries data`() = runTest {
        val countriesMap = listOf(
            TestCountryModelProvider.getTestCountryModel()
        ).associateBy({ it.cca3 }, { it })
        val remoteMap = listOf(
            TestCountryModelProvider.getTestCountryModel(),
            TestCountryModelProvider.getTestCountryModel(name = "Another Country", cca3 = "ANC")
        ).associateBy({ it.cca3 }, { it })

        viewModel.countriesState.test {
            assertEquals(expected = CountriesUiState.Loading, actual = awaitItem())
            (countriesRepository as FakeTestCountriesRepository).updateCountriesMap(countriesMap)
            assertEquals(expected = CountriesUiState.Success(countriesMap), actual = awaitItem())
            (countriesRepository as FakeTestCountriesRepository).setRemoteMap(remoteMap)
            viewModel.refreshCountyList()
            assertEquals(expected = CountriesUiState.Success(remoteMap), actual = awaitItem())
        }
    }

}
