package com.spitzer.sample.screenshot.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.spitzer.sample.configuration.FontScale
import com.spitzer.sample.configuration.setContentAndCapture
import com.spitzer.ui.testing.screenshotPreview.layout.ScaffoldLayout_ScreenshotTest
import com.spitzer.ui.testing.screenshotPreview.layout.ScaffoldLayout_ScreenshotTest_LargeTAB
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
class ScaffoldScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()
    private val screenshotName = "Layout/Scaffold/scaffold"
    private val LTABScaffoldScreenshotName = "${screenshotName}_LargeTAB"

    @Test
    fun scaffold() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName
        ) { ComposeScaffold() }
    }

    @Test
    fun scaffold_largeTAB() {
        composeRule.setContentAndCapture(
            screenshotName = LTABScaffoldScreenshotName,
        ) { ComposeScaffold_LargeTAB() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun scaffold_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, darkMode = true
        ) { ComposeScaffold() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun scaffold_LargeTAB_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = LTABScaffoldScreenshotName,
            darkMode = true
        ) { ComposeScaffold_LargeTAB() }
    }

    @Test
    fun scaffold_HugeFont() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, fontScale = FontScale.TWO_TO_ONE
        ) { ComposeScaffold() }
    }

    @Test
    fun scaffold_LargeTAB_HugeFont() {
        composeRule.setContentAndCapture(
            screenshotName = LTABScaffoldScreenshotName,
            fontScale = FontScale.TWO_TO_ONE,
        ) { ComposeScaffold_LargeTAB() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun scaffold_HugeFont_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, fontScale = FontScale.TWO_TO_ONE, darkMode = true
        ) { ComposeScaffold() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun scaffold_LargeTAB_HugeFont_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = LTABScaffoldScreenshotName,
            fontScale = FontScale.TWO_TO_ONE,
            darkMode = true
        ) { ComposeScaffold_LargeTAB() }
    }

    @Composable
    private fun ComposeScaffold() {
        ScaffoldLayout_ScreenshotTest()
    }

    @Composable
    private fun ComposeScaffold_LargeTAB() {
        ScaffoldLayout_ScreenshotTest_LargeTAB()
    }
}
