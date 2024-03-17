package com.spitzer.sample.screenshot.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.spitzer.sample.configuration.FontScale
import com.spitzer.sample.configuration.setContentAndCapture
import com.spitzer.ui.testing.screenshotPreview.components.TransparentSearchFieldPreview_ScreenshotTest
import org.junit.Assert.*
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

    private val screenshotName = "Composable/TransparentSearchField/transparentSearchField"

    @Test
    fun allTextFields() {
        composeRule.setContentAndCapture(screenshotName = screenshotName) { ComposeTransparentSearchFields() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun allTextFields_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, darkMode = true
        ) { ComposeTransparentSearchFields() }
    }

    @Test
    fun allTextFields_HugeFont() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, fontScale = FontScale.TWO_TO_ONE
        ) { ComposeTransparentSearchFields() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun allTextFields_HugeFont_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, fontScale = FontScale.TWO_TO_ONE, darkMode = true
        ) { ComposeTransparentSearchFields() }
    }

    @Composable
    private fun ComposeTransparentSearchFields() {
        TransparentSearchFieldPreview_ScreenshotTest()
    }
}
