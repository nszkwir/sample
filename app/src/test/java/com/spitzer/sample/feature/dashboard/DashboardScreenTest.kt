package com.spitzer.sample.feature.dashboard

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.spitzer.model.testing.TestCountryModelProvider
import com.spitzer.ui.R
import com.spitzer.ui.feature.dashboard.DashboardScreen
import com.spitzer.ui.feature.dashboard.DashboardUiState
import com.spitzer.ui.feature.dashboard.asDashboardCountryModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(
    sdk = [33],
    qualifiers = RobolectricDeviceQualifiers.Pixel6
)
class DashboardScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var welcome: String
    private lateinit var loading: String
    private lateinit var settings: String
    private lateinit var flagsAndCoatOfArms: String
    private lateinit var fullInformation: String
    private lateinit var statistics: String
    private lateinit var errorSearchingCountries: String
    private lateinit var weHaventFoundAnyCountry: String

    @Before
    fun appStartup() {
        composeRule.activity.apply {
            welcome = getString(R.string.welcome)
            loading = getString(R.string.loading)
            settings = getString(R.string.settings)
            flagsAndCoatOfArms = getString(R.string.flagsAndCoatOfArms)
            fullInformation = getString(R.string.fullInformation)
            statistics = getString(R.string.statistics)
            errorSearchingCountries = getString(R.string.errorSearchingCountries)
            weHaventFoundAnyCountry = getString(R.string.weHaventFoundAnyCountry)
        }
    }

    @Test
    fun `When DashboardUiState is Loading, LoadingLayout is shown`() {
        composeRule.setContent { DashboardScreen(uiState = DashboardUiState().copy(isLoading = true)) }
        composeRule.onNodeWithContentDescription(loading).assertExists()
        composeRule.onNodeWithContentDescription(settings).assertExists()
        composeRule.onNodeWithText(welcome).assertExists()
    }

    @Test
    fun `When DashboardUiState is Error, ErrorLayout is shown`() {
        // TODO Implement ErrorLayout
        composeRule.setContent { DashboardScreen(uiState = DashboardUiState().copy(isError = true)) }
        composeRule.onNodeWithContentDescription(loading).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(settings).assertExists()
        composeRule.onNodeWithText(welcome).assertExists()
    }

    @Test
    fun `When CountryDetailsUiState is not searching, Dashboard cards are shown`() {
        composeRule.setContent { DashboardScreen(uiState = DashboardUiState().copy(searchIsActive = false)) }
        composeRule.onNodeWithContentDescription(loading).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(settings).assertExists()
        composeRule.onNodeWithText(welcome).assertExists()
        composeRule.onNodeWithContentDescription(flagsAndCoatOfArms).assertExists()
        composeRule.onNodeWithContentDescription(statistics).assertExists()
        composeRule.onNodeWithContentDescription(fullInformation).assertExists()
    }

    @Test
    fun `When there's an error when searching countries, Error Card is shown`() {
        composeRule.setContent {
            DashboardScreen(
                uiState = DashboardUiState().copy(
                    searchIsActive = true,
                    searchingCountriesError = true
                )
            )
        }
        composeRule.onNodeWithContentDescription(loading).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(settings).assertExists()
        composeRule.onNodeWithText(welcome).assertExists()
        composeRule.onNodeWithText(errorSearchingCountries).assertExists()
    }

    @Test
    fun `When the searching for countries is in progress, CircularProgressIndicator is shown`() {
        composeRule.setContent {
            DashboardScreen(
                uiState = DashboardUiState().copy(
                    searchIsActive = true,
                    searchingCountriesProgress = true
                )
            )
        }
        composeRule.onNodeWithContentDescription(loading).assertExists()
        composeRule.onNodeWithContentDescription(settings).assertExists()
        composeRule.onNodeWithText(welcome).assertExists()
        composeRule.onNodeWithText(errorSearchingCountries).assertDoesNotExist()
    }

    @Test
    fun `When the search finishes, Countries are shown`() {
        val countries = listOfNotNull(
            TestCountryModelProvider.getTestCountryModel(cca3 = "ARG", name = "Argentina")
                .asDashboardCountryModel(),
            TestCountryModelProvider.getTestCountryModel(cca3 = "URY", name = "Uruguay")
                .asDashboardCountryModel()
        )
        composeRule.setContent {
            DashboardScreen(
                uiState = DashboardUiState().copy(
                    countries = countries,
                    searchIsActive = true,
                    searchingCountriesProgress = false,
                    searchingCountriesError = false
                )
            )
        }
        composeRule.onNodeWithContentDescription(loading).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(settings).assertExists()
        composeRule.onNodeWithText(welcome).assertExists()
        composeRule.onNodeWithText(errorSearchingCountries).assertDoesNotExist()
        composeRule.onNodeWithText(weHaventFoundAnyCountry).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(countries[0].name).assertExists()
        composeRule.onNodeWithContentDescription(countries[1].name).assertExists()

    }

    @Test
    fun `When the search finishes without a finding, Countries not found Card is shown`() {
        composeRule.setContent {
            DashboardScreen(
                uiState = DashboardUiState().copy(
                    countries = emptyList(),
                    searchIsActive = true,
                    searchingCountriesProgress = false,
                    searchingCountriesError = false
                )
            )
        }
        composeRule.onNodeWithContentDescription(loading).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(settings).assertExists()
        composeRule.onNodeWithText(welcome).assertExists()
        composeRule.onNodeWithText(errorSearchingCountries).assertDoesNotExist()
        composeRule.onNodeWithText(weHaventFoundAnyCountry).assertExists()
    }
}
