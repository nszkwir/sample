package com.spitzer.sample.feature.countries

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.spitzer.model.testing.TestCountryModelProvider
import com.spitzer.sample.configuration.captureMultiDeviceMultiMode
import com.spitzer.ui.feature.countries.CountriesScreen
import com.spitzer.ui.feature.countries.CountriesUiState
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
class CountriesScreenScreenshotTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()
    private val screenshotName = "Feature/CountriesScreen/"
    private val loadingScreenshotName = "${screenshotName}loading"
    private val errorScreenshotName = "${screenshotName}error"
    private val successScreenshotName = "${screenshotName}success"

    @Test
    fun CountriesScreen_Loading() {
        composeRule.captureMultiDeviceMultiMode(screenshotName = loadingScreenshotName) {
            CountriesScreen(uiState = CountriesUiState.Loading)
        }
    }

    @Test
    fun CountriesScreen_Error() {
        // TODO Implement ErrorLayout
        composeRule.captureMultiDeviceMultiMode(screenshotName = errorScreenshotName) {
            CountriesScreen(uiState = CountriesUiState.Error)
        }
    }

    @Test
    fun CountriesScreen_Success() {
        val list = listOf(
            TestCountryModelProvider.getTestCountryModel(cca3 = "ARG", name = "Argentina"),
            TestCountryModelProvider.getTestCountryModel(cca3 = "URY", name = "Uruguay")
        )
        composeRule.captureMultiDeviceMultiMode(screenshotName = successScreenshotName) {
            CountriesScreen(
                uiState = CountriesUiState.Success(
                    list.associateBy({ it.cca3 }, { it })
                )
            )
        }
    }
}
