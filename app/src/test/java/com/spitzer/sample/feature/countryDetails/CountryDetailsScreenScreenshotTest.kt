package com.spitzer.sample.feature.countryDetails

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.spitzer.model.testing.TestCountryModelProvider
import com.spitzer.sample.configuration.captureMultiDeviceMultiMode
import com.spitzer.ui.feature.countryDetails.CountryDetailsScreen
import com.spitzer.ui.feature.countryDetails.CountryDetailsUiState
import com.spitzer.ui.feature.countryDetails.mapToCountryDetailsModel
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
class CountryDetailsScreenScreenshotTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()
    private val screenshotName = "Feature/CountryDetails/"
    private val loadingScreenshotName = "${screenshotName}loading"
    private val errorScreenshotName = "${screenshotName}error"
    private val successScreenshotName = "${screenshotName}success"

    @Test
    fun CountryDetailsScreen_Loading() {
        composeRule.captureMultiDeviceMultiMode(screenshotName = loadingScreenshotName) {
            CountryDetailsScreen(uiState = CountryDetailsUiState())
        }
    }

    @Test
    fun CountryDetailsScreen_Error() {
        // TODO Implement ErrorLayout
        composeRule.captureMultiDeviceMultiMode(screenshotName = errorScreenshotName) {
            CountryDetailsScreen(
                uiState = CountryDetailsUiState(
                    isLoading = false,
                    isError = true
                )
            )
        }
    }

    @Test
    fun CountryDetailsScreen_Success() {
        val country = TestCountryModelProvider.getTestCountryModel()
        val detailsCountryModel = country.mapToCountryDetailsModel(emptyMap())
        composeRule.captureMultiDeviceMultiMode(screenshotName = successScreenshotName) {
            CountryDetailsScreen(
                uiState = CountryDetailsUiState(
                    isLoading = false,
                    isError = false,
                    country = detailsCountryModel
                )
            )
        }
    }
}
