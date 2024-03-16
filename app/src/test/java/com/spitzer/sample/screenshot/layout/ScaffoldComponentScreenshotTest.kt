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
import com.spitzer.ui.testing.screenshotPreview.layout.FABLayout_ScreenshotTest
import com.spitzer.ui.testing.screenshotPreview.layout.LTABLayout_ScreenshotTest
import com.spitzer.ui.testing.screenshotPreview.layout.LTABLayout_ScreenshotTest2
import com.spitzer.ui.testing.screenshotPreview.layout.TABLayout_ScreenshotTest
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
class ScaffoldComponentScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()
    private val screenshotName = "Layout/Scaffold/scaffold_component"

    /** FAB */
    private val fabScreenshotName = "${screenshotName}_FAB"

    @Test
    fun fab() {
        composeRule.setContent {
            ComposeFAB()
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(fabScreenshotName, "Pixel6", false),
                roborazziOptions = DefaultRoborazziOptions
            )
    }

    @Test
    @Config(qualifiers = "+night")
    fun fab_DarkMode() {
        composeRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                TestHarness(darkMode = true) {
                    ComposeFAB()
                }
            }
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(fabScreenshotName, "Pixel6", true),
                roborazziOptions = DefaultRoborazziOptions
            )
    }


    @Test
    fun fab_HugeFont() {
        val fontScale = FontScale.TWO_TO_ONE
        composeRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                TestHarness(fontScale = fontScale.value) {
                    ComposeFAB()
                }
            }
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(fabScreenshotName, "Pixel6", false, fontScale.description),
                roborazziOptions = DefaultRoborazziOptions
            )
    }

    @Test
    @Config(qualifiers = "+night")
    fun fab_HugeFont_DarkMode() {
        val fontScale = FontScale.TWO_TO_ONE
        composeRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                TestHarness(fontScale = 2f, darkMode = true) {
                    ComposeFAB()
                }
            }
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(fabScreenshotName, "Pixel6", true, fontScale.description),
                roborazziOptions = DefaultRoborazziOptions
            )
    }

    @Composable
    private fun ComposeFAB() {
        FABLayout_ScreenshotTest()
    }

    /** Large Top App Bar */
    private val largeTABScreenshotName = "${screenshotName}_LTAB"
    private val largeTABScreenshotName2 = "${screenshotName}_LTAB2"

    @Test
    fun ltab() {
        composeRule.setContent {
            ComposeLTAB()
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(largeTABScreenshotName, "Pixel6", false),
                roborazziOptions = DefaultRoborazziOptions
            )
    }

    @Test
    fun ltab2() {
        composeRule.setContent {
            ComposeLTAB2()
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(largeTABScreenshotName2, "Pixel6", false),
                roborazziOptions = DefaultRoborazziOptions
            )
    }

    @Test
    @Config(qualifiers = "+night")
    fun ltab_DarkMode() {
        composeRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                TestHarness(darkMode = true) {
                    ComposeLTAB()
                }
            }
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(largeTABScreenshotName, "Pixel6", true),
                roborazziOptions = DefaultRoborazziOptions
            )
    }

    @Test
    @Config(qualifiers = "+night")
    fun ltab_DarkMode2() {
        composeRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                TestHarness(darkMode = true) {
                    ComposeLTAB2()
                }
            }
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(largeTABScreenshotName2, "Pixel6", true),
                roborazziOptions = DefaultRoborazziOptions
            )
    }

    @Test
    fun ltab_HugeFont() {
        val fontScale = FontScale.TWO_TO_ONE
        composeRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                TestHarness(fontScale = fontScale.value) {
                    ComposeLTAB()
                }
            }
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(
                    largeTABScreenshotName,
                    "Pixel6",
                    false,
                    fontScale.description
                ),
                roborazziOptions = DefaultRoborazziOptions
            )
    }

    @Test
    fun ltab_HugeFont2() {
        val fontScale = FontScale.TWO_TO_ONE
        composeRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                TestHarness(fontScale = fontScale.value) {
                    ComposeLTAB2()
                }
            }
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(
                    largeTABScreenshotName2,
                    "Pixel6",
                    false,
                    fontScale.description
                ),
                roborazziOptions = DefaultRoborazziOptions
            )
    }

    @Test
    @Config(qualifiers = "+night")
    fun ltab_HugeFont_DarkMode() {
        val fontScale = FontScale.TWO_TO_ONE
        composeRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                TestHarness(fontScale = 2f, darkMode = true) {
                    ComposeLTAB()
                }
            }
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(
                    largeTABScreenshotName,
                    "Pixel6",
                    true,
                    fontScale.description
                ),
                roborazziOptions = DefaultRoborazziOptions
            )
    }

    @Test
    @Config(qualifiers = "+night")
    fun ltab_HugeFont_DarkMode2() {
        val fontScale = FontScale.TWO_TO_ONE
        composeRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                TestHarness(fontScale = 2f, darkMode = true) {
                    ComposeLTAB2()
                }
            }
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(
                    largeTABScreenshotName2,
                    "Pixel6",
                    true,
                    fontScale.description
                ),
                roborazziOptions = DefaultRoborazziOptions
            )
    }

    @Composable
    private fun ComposeLTAB() {
        LTABLayout_ScreenshotTest()
    }

    @Composable
    private fun ComposeLTAB2() {
        LTABLayout_ScreenshotTest2()
    }

    /** Top App Bar */
    private val tabScreenshotName = "${screenshotName}_TAB"

    @Test
    fun tab() {
        composeRule.setContent {
            ComposeTAB()
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(tabScreenshotName, "Pixel6", false),
                roborazziOptions = DefaultRoborazziOptions
            )
    }

    @Test
    @Config(qualifiers = "+night")
    fun tab_DarkMode() {
        composeRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                TestHarness(darkMode = true) {
                    ComposeTAB()
                }
            }
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(tabScreenshotName, "Pixel6", true),
                roborazziOptions = DefaultRoborazziOptions
            )
    }


    @Test
    fun tab_HugeFont() {
        val fontScale = FontScale.TWO_TO_ONE
        composeRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                TestHarness(fontScale = fontScale.value) {
                    ComposeTAB()
                }
            }
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(tabScreenshotName, "Pixel6", false, fontScale.description),
                roborazziOptions = DefaultRoborazziOptions
            )
    }

    @Test
    @Config(qualifiers = "+night")
    fun tab_HugeFont_DarkMode() {
        val fontScale = FontScale.TWO_TO_ONE
        composeRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                TestHarness(fontScale = 2f, darkMode = true) {
                    ComposeTAB()
                }
            }
        }

        composeRule.onRoot()
            .captureRoboImage(
                getScreenshotFilePath(tabScreenshotName, "Pixel6", true, fontScale.description),
                roborazziOptions = DefaultRoborazziOptions
            )
    }

    @Composable
    private fun ComposeTAB() {
        TABLayout_ScreenshotTest()
    }
}
