package com.spitzer.sample.screenshot.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.spitzer.sample.configuration.FontScale
import com.spitzer.sample.configuration.setContentAndCapture
import com.spitzer.ui.testing.screenshotPreview.components.OutlinedLoadingButtonPreview_ScreenshotTest
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
class OutlinedLoadingButtonScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()
    private val screenshotName = "Composable/OutlinedLoadingButton/outlinedLoadingButton"

    @Test
    fun allButtons() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName
        ) { ComposeOutlinedLoadingButton() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun allButtons_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, darkMode = true
        ) { ComposeOutlinedLoadingButton() }
    }

    @Test
    fun allButtons_HugeFont() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, fontScale = FontScale.TWO_TO_ONE
        ) { ComposeOutlinedLoadingButton() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun allButtons_HugeFont_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, fontScale = FontScale.TWO_TO_ONE, darkMode = true
        ) { ComposeOutlinedLoadingButton() }
    }

    @Composable
    private fun ComposeOutlinedLoadingButton() {
        OutlinedLoadingButtonPreview_ScreenshotTest()
    }
}
