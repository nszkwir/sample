package com.spitzer.sample.screenshot.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import com.google.accompanist.testharness.TestHarness
import com.spitzer.model.testing.TestCountryModelProvider
import com.spitzer.sample.configuration.DefaultRoborazziOptions
import com.spitzer.sample.configuration.FontScale
import com.spitzer.sample.configuration.getScreenshotFilePath
import com.spitzer.ui.R
import com.spitzer.ui.components.dashboardCard.DashboardCountryCard
import com.spitzer.ui.feature.dashboard.asDashboardCountryModel
import com.spitzer.ui.theme.SampleTheme
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
class DashboardCountryCardScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val screenshotName = "Composable/DashboardCountryCard/dashboardCountryCard"


    @Test
    fun allCards() {
        composeRule.setContent {
            ComposeDashboardCountryCard()
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(screenshotName, "Pixel6", false),
                roborazziOptions = DefaultRoborazziOptions
            )
    }

    @Test
    @Config(qualifiers = "+night")
    fun allCards_DarkMode() {
        composeRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                TestHarness(darkMode = true) {
                    ComposeDashboardCountryCard()
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
    fun allCards_HugeFont() {
        val fontScale = FontScale.TWO_TO_ONE
        composeRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                TestHarness(fontScale = fontScale.value) {
                    ComposeDashboardCountryCard()
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
    fun allCards_HugeFont_DarkMode() {
        val fontScale = FontScale.TWO_TO_ONE
        composeRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                TestHarness(fontScale = 2f, darkMode = true) {
                    ComposeDashboardCountryCard()
                }
            }
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(screenshotName, "Pixel6", true, fontScale.description),
                roborazziOptions = DefaultRoborazziOptions
            )
    }

    @Composable
    private fun ComposeDashboardCountryCard() {
        SampleTheme {
            DashboardCountryCard(
                country = TestCountryModelProvider.getTestCountryModel()
                    .asDashboardCountryModel()!!,
                painter = painterResource(R.drawable.baseline_broken_image_no_padding),
                onClick = {},
            )
        }
    }
}
