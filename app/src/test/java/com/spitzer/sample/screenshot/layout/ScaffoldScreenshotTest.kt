package com.spitzer.sample.screenshot.layout

import androidx.compose.runtime.Composable
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
    private val largeTABScaffoldScreenshotName = "${screenshotName}_LargeTAB"

    @Test
    fun scaffold() {
        composeRule.setContent {
            ComposeScaffold()
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(screenshotName, "Pixel6", false),
                roborazziOptions = DefaultRoborazziOptions
            )
    }

    @Test
    fun scaffold_largeTAB() {
        composeRule.setContent {
            ComposeScaffold_LargeTAB()
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(largeTABScaffoldScreenshotName, "Pixel6", false),
                roborazziOptions = DefaultRoborazziOptions
            )
    }

    @Test
    @Config(qualifiers = "+night")
    fun scaffold_DarkMode() {
        composeRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                TestHarness(darkMode = true) {
                    ComposeScaffold()
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
    @Config(qualifiers = "+night")
    fun scaffold_LargeTAB_DarkMode() {
        composeRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                TestHarness(darkMode = true) {
                    ComposeScaffold_LargeTAB()
                }
            }
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(largeTABScaffoldScreenshotName, "Pixel6", true),
                roborazziOptions = DefaultRoborazziOptions
            )
    }

    @Test
    fun scaffold_HugeFont() {
        val fontScale = FontScale.TWO_TO_ONE
        composeRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                TestHarness(fontScale = fontScale.value) {
                    ComposeScaffold()
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
    fun scaffold_LargeTAB_HugeFont() {
        val fontScale = FontScale.TWO_TO_ONE
        composeRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                TestHarness(fontScale = fontScale.value) {
                    ComposeScaffold_LargeTAB()
                }
            }
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(
                    largeTABScaffoldScreenshotName,
                    "Pixel6",
                    false,
                    fontScale.description
                ),
                roborazziOptions = DefaultRoborazziOptions
            )
    }

    @Test
    @Config(qualifiers = "+night")
    fun scaffold_HugeFont_DarkMode() {
        val fontScale = FontScale.TWO_TO_ONE
        composeRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                TestHarness(fontScale = 2f, darkMode = true) {
                    ComposeScaffold()
                }
            }
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(screenshotName, "Pixel6", true, fontScale.description),
                roborazziOptions = DefaultRoborazziOptions
            )
    }

    @Test
    @Config(qualifiers = "+night")
    fun scaffold_LargeTAB_HugeFont_DarkMode() {
        val fontScale = FontScale.TWO_TO_ONE
        composeRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                TestHarness(fontScale = 2f, darkMode = true) {
                    ComposeScaffold_LargeTAB()
                }
            }
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(
                    largeTABScaffoldScreenshotName,
                    "Pixel6",
                    true,
                    fontScale.description
                ),
                roborazziOptions = DefaultRoborazziOptions
            )
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

