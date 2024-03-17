package com.spitzer.sample.screenshot.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.spitzer.sample.configuration.FontScale
import com.spitzer.sample.configuration.setContentAndCapture
import com.spitzer.ui.testing.screenshotPreview.layout.CountryListLayout_ScreenshotTest
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
class TransparentSearchFieldScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()
    private val screenshotName = "Layout/CountryList/countryList"

    @Test
    fun countryList() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName
        ) { ComposeCountryList() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun countryList_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, darkMode = true
        ) { ComposeCountryList() }
    }

    @Test
    fun countryList_HugeFont() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, fontScale = FontScale.TWO_TO_ONE
        ) { ComposeCountryList() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun countryList_HugeFont_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, fontScale = FontScale.TWO_TO_ONE, darkMode = true
        ) { ComposeCountryList() }
    }

    @Composable
    private fun ComposeCountryList() {
        CountryListLayout_ScreenshotTest()
    }
}
