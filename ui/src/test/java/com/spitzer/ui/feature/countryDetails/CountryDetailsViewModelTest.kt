package com.spitzer.ui.feature.countryDetails

import app.cash.turbine.test
import com.spitzer.common.testing.di.MainDispatcherRule
import com.spitzer.data.repository.CountriesRepository
import com.spitzer.data.repository.LanguagesRepository
import com.spitzer.model.data.CountryModel
import com.spitzer.model.testing.TestCountryModelProvider
import com.spitzer.model.testing.TestISOLanguageProvider
import com.spitzer.ui.feature.FakeTestCountriesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class CountryDetailsViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    // Mocks
    private val countriesRepository: CountriesRepository = FakeTestCountriesRepository()
    private val languagesRepository: LanguagesRepository = mockk<LanguagesRepository>()

    private lateinit var viewModel: CountryDetailsViewModel

    private val exceptionMessage = "Test exception message"
    private val languagesMap = TestISOLanguageProvider.getISOLanguageList()
        .associateBy({ it.code3 }, { it })

    @Before
    fun setup() {
        viewModel = CountryDetailsViewModel(countriesRepository, languagesRepository)
        coEvery { languagesRepository.getLanguages() } returns languagesMap
    }

    @Test
    fun `initializing viewModel sets uiState as default values`() = runTest {
        assertEquals(actual = viewModel.uiState.value, expected = CountryDetailsUiState())
    }

    @Test
    fun `getCountryData with null cca3 code updates uiState`() = runTest {
        val cca3: String? = null
        var uiState = CountryDetailsUiState()

        viewModel.uiState.test {
            viewModel.getCountryData(cca3)
            assertEquals(expected = uiState, actual = awaitItem())
            uiState = uiState.copy(isLoading = false, isError = true)
            assertEquals(expected = uiState, actual = awaitItem())
            expectNoEvents()
        }
    }

    @Test
    fun `getCountryData with empty cca3 code updates uiState`() = runTest {
        val cca3: String? = ""
        var uiState = CountryDetailsUiState()

        viewModel.uiState.test {
            viewModel.getCountryData(cca3)
            assertEquals(expected = uiState, actual = awaitItem())
            uiState = uiState.copy(isLoading = false, isError = true)
            assertEquals(expected = uiState, actual = awaitItem())
            expectNoEvents()
        }
    }

    @Test
    fun `getCountryData fails finding country updates uiState`() = runTest {
        val cca3: String? = "PAR"
        var uiState = CountryDetailsUiState()

        viewModel.uiState.test {
            viewModel.getCountryData(cca3)
            assertEquals(expected = uiState, actual = awaitItem())
            uiState = uiState.copy(isLoading = false, isError = true)
            assertEquals(expected = uiState, actual = awaitItem())
            expectNoEvents()
        }
    }

    @Test
    fun `getCountryData finds country updates uiState`() = runTest {
        val cca3: String? = "ARG"
        var uiState = CountryDetailsUiState()
        val country: CountryModel = TestCountryModelProvider.getTestCountryModel()
        (countriesRepository as FakeTestCountriesRepository).setCountry(country)

        viewModel.uiState.test {
            viewModel.getCountryData(cca3)
            assertEquals(expected = uiState, actual = awaitItem())
            uiState = uiState.copy(
                isLoading = false,
                isError = false,
                country = country.mapToCountryDetailsModel(languagesMap)
            )
            assertEquals(expected = uiState, actual = awaitItem())
            expectNoEvents()
        }
    }
}
