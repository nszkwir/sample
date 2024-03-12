package com.spitzer.ui.feature.dashboard

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
import kotlin.test.assertTrue

class DashboardViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    // Mocks
    private val countriesRepository: CountriesRepository = FakeTestCountriesRepository()

    private lateinit var viewModel: DashboardViewModel

    private val exceptionMessage = "Test exception message"

    @Before
    fun setup() {
        viewModel = DashboardViewModel(countriesRepository)
    }

    @Test
    fun `initializing viemodel sets uiState with default values`() = runTest {
        assertEquals(actual = viewModel.uiState.value, expected = DashboardUiState())
    }

    @Test
    fun `onClearSearchText triggers onSearchTextChanged with empty searchText`() = runTest {
        val searchText = ""

        viewModel.uiState.test {
            viewModel.clearSearchText()
            assertEquals(expected = DashboardUiState(), actual = awaitItem())
            assertEquals(
                expected = DashboardUiState().copy(
                    searchText = searchText,
                    searchIsActive = false
                ),
                actual = awaitItem()
            )
            expectNoEvents()
        }
    }

    @Test
    fun `onSearchTextChange with empty searchTextSize`() = runTest {
        val searchText = ""

        viewModel.uiState.test {
            viewModel.onSearchTextChange(searchText)
            assertEquals(expected = DashboardUiState(), actual = awaitItem())
            assertEquals(
                expected = DashboardUiState().copy(
                    searchText = searchText,
                    searchIsActive = false
                ),
                actual = awaitItem()
            )
            expectNoEvents()
        }
    }

    @Test
    fun `onSearchTextChange with searchTextSize less than 2`() = runTest {
        val searchText = "a"
        viewModel.uiState.test {
            viewModel.onSearchTextChange(searchText)
            assertEquals(expected = DashboardUiState(), actual = awaitItem())
            assertEquals(
                expected = DashboardUiState().copy(
                    searchText = searchText,
                    searchIsActive = false
                ),
                actual = awaitItem()
            )
            expectNoEvents()
        }
    }

    @Test
    fun `onSearchTextChange with searchTextSize bigger than 2 no finds`() = runTest {
        val searchText = "Bra"
        val countriesMap = listOf(
            TestCountryModelProvider.getTestCountryModel(
                name = "Argentina",
                cca3 = "ARG",
                tags = "Argentina"
            ),
            TestCountryModelProvider.getTestCountryModel(
                name = "Uruguay",
                cca3 = "URY",
                tags = "Uruguay"
            )
        ).associateBy({ it.cca3 }, { it })

        val foundCountries = emptyList<DashboardCountryModel>()
        var uiState = DashboardUiState()

        viewModel.uiState.test {
            viewModel.onSearchTextChange(searchText)
            assertEquals(expected = uiState, actual = awaitItem())
            uiState = uiState.copy(
                searchText = searchText,
                searchIsActive = true,
            )
            assertEquals(expected = uiState, actual = awaitItem())
            uiState = uiState.copy(
                searchingCountriesProgress = true,
                searchingCountriesError = false,
                countries = null
            )
            assertEquals(expected = uiState, actual = awaitItem())
            (countriesRepository as FakeTestCountriesRepository).updateCountriesMap(countriesMap)
            uiState = uiState.copy(
                searchingCountriesProgress = false,
                searchingCountriesError = false,
                countries = foundCountries
            )
            assertEquals(expected = uiState, actual = awaitItem())
            expectNoEvents()
        }
    }

    @Test
    fun `onSearchTextChange with searchTextSize bigger than 2 finds countries`() = runTest {
        val searchText = "Arg"
        val countriesMap = listOf(
            TestCountryModelProvider.getTestCountryModel(
                name = "Argentina",
                cca3 = "ARG",
                tags = "Argentina argentina"
            ),
            TestCountryModelProvider.getTestCountryModel(
                name = "Uruguay",
                cca3 = "URY",
                tags = "Uruguay uruguay"
            )
        ).associateBy({ it.cca3 }, { it })

        val foundCountries = countriesMap.mapNotNull {
            if (it.value.tags.contains(searchText.lowercase())) it.value.asDashboardCountryModel()
            else null
        }
        var uiState = DashboardUiState()

        (countriesRepository as FakeTestCountriesRepository).updateCountriesMap(countriesMap)

        viewModel.uiState.test {
            viewModel.onSearchTextChange(searchText)
            assertEquals(expected = uiState, actual = awaitItem())
            uiState = uiState.copy(
                searchText = searchText,
                searchIsActive = true,
            )
            assertEquals(expected = uiState, actual = awaitItem())
            uiState = uiState.copy(
                searchingCountriesProgress = true,
                searchingCountriesError = false,
                countries = null
            )
            assertEquals(expected = uiState, actual = awaitItem())
            (countriesRepository as FakeTestCountriesRepository).updateCountriesMap(countriesMap)
            uiState = uiState.copy(
                searchingCountriesProgress = false,
                searchingCountriesError = false,
                countries = foundCountries
            )
            val state = awaitItem()
            assertEquals(expected = uiState, actual = state)
            assertTrue(state.countries?.isNotEmpty() == true)
            expectNoEvents()
        }
    }

    @Test
    fun `onSearchTextChange with searchTextSize bigger than 2 exception occurs`() = runTest {
        (countriesRepository as FakeTestCountriesRepository).emitException(exceptionMessage)
        viewModel = DashboardViewModel(countriesRepository)
        var uiState = DashboardUiState()
        val searchText = "Arg"

        viewModel.uiState.test {
            viewModel.onSearchTextChange(searchText)
            assertEquals(expected = uiState, actual = awaitItem())
            uiState = uiState.copy(
                searchText = searchText,
                searchIsActive = true,
            )
            assertEquals(expected = uiState, actual = awaitItem())
            uiState = uiState.copy(
                searchingCountriesProgress = false,
                searchingCountriesError = true,
                countries = null
            )
            assertEquals(expected = uiState, actual = awaitItem())
            expectNoEvents()
        }
    }
}
