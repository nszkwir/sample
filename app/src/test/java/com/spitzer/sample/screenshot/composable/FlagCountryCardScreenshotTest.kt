package com.spitzer.sample.screenshot.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.test.junit4.createComposeRule
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.spitzer.model.testing.TestCountryModelProvider
import com.spitzer.sample.configuration.FontScale
import com.spitzer.sample.configuration.setContentAndCapture
import com.spitzer.ui.R
import com.spitzer.ui.components.countryCard.FlagCountryCard
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
class FlagCountryCardScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()
    private val screenshotName = "Composable/FlagCountryCard/flagCountryCard"

    @Test
    fun allCards() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName
        ) { ComposeFlagCountryCard() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun allCards_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, darkMode = true
        ) { ComposeFlagCountryCard() }
    }


    @Test
    fun allCards_HugeFont() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, fontScale = FontScale.TWO_TO_ONE
        ) { ComposeFlagCountryCard() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun allCards_HugeFont_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, fontScale = FontScale.TWO_TO_ONE, darkMode = true
        ) { ComposeFlagCountryCard() }
    }

    @Composable
    private fun ComposeFlagCountryCard() {
        SampleTheme {
            FlagCountryCard(
                country = TestCountryModelProvider.getTestCountryModel(),
                painter = painterResource(R.drawable.baseline_broken_image_no_padding),
                onClick = {},
                onLongClick = {}
            )
        }
    }
}
