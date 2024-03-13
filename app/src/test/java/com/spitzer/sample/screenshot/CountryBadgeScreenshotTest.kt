package com.spitzer.sample.screenshot

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import com.google.accompanist.testharness.TestHarness
import com.spitzer.sample.configuration.DefaultRoborazziOptions
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

    @Test
    fun allCountryBadges() {
        composeRule.setContent {
            CountryBadge_ScreenshotTestFunction()
        }

        composeRule.onRoot()
            .captureRoboImage(
                "src/test/screenshots/CountryBadge/allCountryBadges.png",
                roborazziOptions = DefaultRoborazziOptions
            )
    }


    @Test
    fun allCountryBadges_HugeFont() {
        composeRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                TestHarness(fontScale = 2f) {
                    CountryBadge_ScreenshotTestFunction()
                }
            }
        }

        composeRule.onRoot()
            .captureRoboImage(
                "src/test/screenshots/CountryBadge/allCountryBadges_HugeFont.png",
                roborazziOptions = DefaultRoborazziOptions
            )
    }
}
