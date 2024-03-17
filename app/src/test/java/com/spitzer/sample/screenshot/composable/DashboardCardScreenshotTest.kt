package com.spitzer.sample.screenshot.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.spitzer.sample.configuration.FontScale
import com.spitzer.sample.configuration.setContentAndCapture
import com.spitzer.ui.testing.screenshotPreview.components.DashboardCardPreview_ScreenshotTest
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
class DashboardCardScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()
    private val screenshotName = "Composable/DashboardCard/dashboardCard"

    @Test
    fun allCards() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName
        ) { ComposeDashboardCard() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun allCards_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, darkMode = true
        ) { ComposeDashboardCard() }
    }

    @Test
    fun allCards_HugeFont() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, fontScale = FontScale.TWO_TO_ONE
        ) { ComposeDashboardCard() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun allCards_HugeFont_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, fontScale = FontScale.TWO_TO_ONE, darkMode = true
        ) { ComposeDashboardCard() }
    }

    @Composable
    private fun ComposeDashboardCard() {
        DashboardCardPreview_ScreenshotTest()
    }
}
