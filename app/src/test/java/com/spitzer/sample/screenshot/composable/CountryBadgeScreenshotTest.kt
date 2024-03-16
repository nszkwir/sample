package com.spitzer.sample.screenshot.composable

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import com.google.accompanist.testharness.TestHarness
import com.spitzer.sample.configuration.DefaultRoborazziOptions
import com.spitzer.sample.configuration.FontScale
import com.spitzer.sample.configuration.getScreenshotFilePath
import com.spitzer.ui.components.badges.CountryBadge_ScreenshotTestFunction
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
        composeRule.setContent {
            CountryBadge_ScreenshotTestFunction()
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(screenshotName, "Pixel6", false),
                roborazziOptions = DefaultRoborazziOptions
            )
    }

    @Test
    @Config(qualifiers = "+night")
    fun allCountryBadges_DarkMode() {
        composeRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                TestHarness(darkMode = true) {
                    CountryBadge_ScreenshotTestFunction()
                }
            }
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(screenshotName, "Pixel6", true),
                roborazziOptions = DefaultRoborazziOptions
            )
    }


    @Test
    fun allCountryBadges_HugeFont() {
        val fontScale = FontScale.TWO_TO_ONE
        composeRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                TestHarness(fontScale = fontScale.value) {
                    CountryBadge_ScreenshotTestFunction()
                }
            }
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(screenshotName, "Pixel6", false, fontScale.description),
                roborazziOptions = DefaultRoborazziOptions
            )
    }

    @Test
    @Config(qualifiers = "+night")
    fun allCountryBadges_HugeFont_DarkMode() {
        val fontScale = FontScale.TWO_TO_ONE
        composeRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                TestHarness(fontScale = 2f, darkMode = true) {
                    CountryBadge_ScreenshotTestFunction()
                }
            }
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(screenshotName, "Pixel6", true, fontScale.description),
                roborazziOptions = DefaultRoborazziOptions
            )
    }
}
