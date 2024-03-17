package com.spitzer.sample.screenshot.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.spitzer.sample.configuration.FontScale
import com.spitzer.sample.configuration.setContentAndCapture
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
        composeRule.setContentAndCapture(
            screenshotName = fabScreenshotName
        ) { ComposeFAB() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun fab_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = fabScreenshotName, darkMode = true
        ) { ComposeFAB() }
    }

    @Test
    fun fab_HugeFont() {
        composeRule.setContentAndCapture(
            screenshotName = fabScreenshotName, fontScale = FontScale.TWO_TO_ONE
        ) { ComposeFAB() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun fab_HugeFont_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = fabScreenshotName, fontScale = FontScale.TWO_TO_ONE, darkMode = true
        ) { ComposeFAB() }
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
        composeRule.setContentAndCapture(
            screenshotName = largeTABScreenshotName
        ) { ComposeLTAB() }
    }

    @Test
    fun ltab2() {
        composeRule.setContentAndCapture(
            screenshotName = largeTABScreenshotName2
        ) { ComposeLTAB2() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun ltab_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = largeTABScreenshotName, darkMode = true
        ) { ComposeLTAB() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun ltab_DarkMode2() {
        composeRule.setContentAndCapture(
            screenshotName = largeTABScreenshotName2, darkMode = true
        ) { ComposeLTAB2() }
    }

    @Test
    fun ltab_HugeFont() {
        composeRule.setContentAndCapture(
            screenshotName = largeTABScreenshotName, fontScale = FontScale.TWO_TO_ONE,
        ) { ComposeLTAB() }
    }

    @Test
    fun ltab_HugeFont2() {
        composeRule.setContentAndCapture(
            screenshotName = largeTABScreenshotName2, fontScale = FontScale.TWO_TO_ONE,
        ) { ComposeLTAB2() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun ltab_HugeFont_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = largeTABScreenshotName,
            fontScale = FontScale.TWO_TO_ONE,
            darkMode = true
        ) { ComposeLTAB() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun ltab_HugeFont_DarkMode2() {
        composeRule.setContentAndCapture(
            screenshotName = largeTABScreenshotName2,
            fontScale = FontScale.TWO_TO_ONE,
            darkMode = true
        ) { ComposeLTAB2() }
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
        composeRule.setContentAndCapture(
            screenshotName = tabScreenshotName
        ) { ComposeTAB() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun tab_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = tabScreenshotName, darkMode = true
        ) { ComposeTAB() }
    }

    @Test
    fun tab_HugeFont() {
        composeRule.setContentAndCapture(
            screenshotName = tabScreenshotName, fontScale = FontScale.TWO_TO_ONE
        ) { ComposeTAB() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun tab_HugeFont_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = tabScreenshotName, fontScale = FontScale.TWO_TO_ONE, darkMode = true
        ) { ComposeTAB() }
    }

    @Composable
    private fun ComposeTAB() {
        TABLayout_ScreenshotTest()
    }
}
