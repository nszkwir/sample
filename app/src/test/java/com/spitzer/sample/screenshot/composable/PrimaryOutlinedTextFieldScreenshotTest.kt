package com.spitzer.sample.screenshot.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.spitzer.sample.configuration.FontScale
import com.spitzer.sample.configuration.setContentAndCapture
import com.spitzer.ui.testing.screenshotPreview.components.PrimaryOutlinedTexFieldPreview_ScreenshotTest
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
class PrimaryOutlinedTextFieldScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()
    private val screenshotName = "Composable/PrimaryOutlinedTextField/primaryOutlinedTextField"

    @Test
    fun allTextFields() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName
        ) { ComposePrimaryOutlinedTextFields() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun allTextFields_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, darkMode = true
        ) { ComposePrimaryOutlinedTextFields() }
    }

    @Test
    fun allTextFields_HugeFont() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, fontScale = FontScale.TWO_TO_ONE
        ) { ComposePrimaryOutlinedTextFields() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun allTextFields_HugeFont_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, fontScale = FontScale.TWO_TO_ONE, darkMode = true
        ) { ComposePrimaryOutlinedTextFields() }
    }

    @Composable
    private fun ComposePrimaryOutlinedTextFields() {
        PrimaryOutlinedTexFieldPreview_ScreenshotTest()
    }
}
