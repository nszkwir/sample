package com.spitzer.sample.screenshot.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.captureRoboImage
import com.google.accompanist.testharness.TestHarness
import com.spitzer.sample.configuration.DefaultRoborazziOptions
import com.spitzer.sample.configuration.FontScale
import com.spitzer.sample.configuration.getScreenshotFilePath
import com.spitzer.sample.configuration.setContentAndCapture
import com.spitzer.ui.testing.screenshotPreview.components.CountryBadge_ScreenshotTestFunction
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
class CountryBadgeScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()
    private val screenshotName = "Composable/CountryBadge/allCountryBadges"


    @Test
    fun allCountryBadges() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, fontScale = FontScale.ONE_TO_ONE, darkMode = false
        ) { ComposeBadges() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun allCountryBadges_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, fontScale = FontScale.ONE_TO_ONE, darkMode = true
        ) { ComposeBadges() }
    }


    @Test
    fun allCountryBadges_HugeFont() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, fontScale = FontScale.TWO_TO_ONE, darkMode = false
        ) { ComposeBadges() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun allCountryBadges_HugeFont_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, fontScale = FontScale.TWO_TO_ONE, darkMode = true
        ) { ComposeBadges() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun allCountryBadges_HugeFontX2_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, fontScale = FontScale.FOUR_TO_ONE, darkMode = true
        ) { ComposeBadges() }
    }


    @Composable
    private fun ComposeBadges() {
        CountryBadge_ScreenshotTestFunction()
    }
}

