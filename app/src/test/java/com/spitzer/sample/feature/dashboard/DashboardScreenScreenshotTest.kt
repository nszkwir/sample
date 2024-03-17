package com.spitzer.sample.feature.dashboard

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.spitzer.model.testing.TestCountryModelProvider
import com.spitzer.sample.configuration.captureMultiDeviceMultiMode
import com.spitzer.ui.feature.dashboard.DashboardScreen
import com.spitzer.ui.feature.dashboard.DashboardUiState
import com.spitzer.ui.feature.dashboard.asDashboardCountryModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(
    sdk = [33]
)
class DashboardScreenScreenshotTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()
    private val screenshotName = "Feature/Dashboard/"
    private val loadingScreenshotName = "${screenshotName}loading"
    private val errorScreenshotName = "${screenshotName}error"
    private val emptyScreenshotName = "${screenshotName}empty"
    private val successScreenshotName = "${screenshotName}success"
    private val notSearchingScreenshotName = "${screenshotName}notSearching"
    private val searchErrorScreenshotName = "${screenshotName}searchError"
    private val searchProgressScreenshotName = "${screenshotName}searchProgress"

    @Test
    fun DashboardScreen_Loading() {
        composeRule.captureMultiDeviceMultiMode(screenshotName = loadingScreenshotName) {
            DashboardScreen(uiState = DashboardUiState().copy(isLoading = true))
        }
    }

    @Test
    fun CountryDetailsScreen_Error() {
        // TODO Implement ErrorLayout
        composeRule.captureMultiDeviceMultiMode(screenshotName = errorScreenshotName) {
            DashboardScreen(uiState = DashboardUiState().copy(isLoading = false, isError = true))
        }
    }

    @Test
    fun CountryDetailsScreen_SearchInactive() {
        composeRule.captureMultiDeviceMultiMode(screenshotName = notSearchingScreenshotName) {
            DashboardScreen(uiState = DashboardUiState().copy(searchIsActive = false))
        }
    }

    @Test
    fun CountryDetailsScreen_SearchError() {
        composeRule.captureMultiDeviceMultiMode(screenshotName = searchErrorScreenshotName) {
            DashboardScreen(
                uiState = DashboardUiState().copy(
                    searchIsActive = true,
                    searchingCountriesError = true
                )
            )
        }
    }

    @Test
    fun CountryDetailsScreen_SearchProgress() {
        composeRule.captureMultiDeviceMultiMode(screenshotName = searchProgressScreenshotName) {
            DashboardScreen(
                uiState = DashboardUiState().copy(
                    searchIsActive = true,
                    searchingCountriesProgress = true
                )
            )
        }
    }

    @Test
    fun CountryDetailsScreen_SearchSuccess() {
        val countries = listOfNotNull(
            TestCountryModelProvider.getTestCountryModel(cca3 = "ARG", name = "Argentina")
                .asDashboardCountryModel(),
            TestCountryModelProvider.getTestCountryModel(cca3 = "URY", name = "Uruguay")
                .asDashboardCountryModel()
        )
        composeRule.captureMultiDeviceMultiMode(screenshotName = successScreenshotName) {
            DashboardScreen(
                uiState = DashboardUiState().copy(
                    countries = countries,
                    searchIsActive = true,
                    searchingCountriesProgress = false,
                    searchingCountriesError = false
                )
            )
        }
    }

    @Test
    fun CountryDetailsScreen_SearchEmpty() {
        composeRule.captureMultiDeviceMultiMode(screenshotName = emptyScreenshotName) {
            DashboardScreen(
                uiState = DashboardUiState().copy(
                    countries = emptyList(),
                    searchIsActive = true,
                    searchingCountriesProgress = false,
                    searchingCountriesError = false
                )
            )
        }
    }

}
