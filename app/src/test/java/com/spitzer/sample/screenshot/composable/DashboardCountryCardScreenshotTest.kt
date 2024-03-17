package com.spitzer.sample.screenshot.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.test.junit4.createComposeRule
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.spitzer.model.testing.TestCountryModelProvider
import com.spitzer.sample.configuration.FontScale
import com.spitzer.sample.configuration.setContentAndCapture
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
        composeRule.setContentAndCapture(
            screenshotName = screenshotName
        ) { ComposeDashboardCountryCard() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun allCards_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, darkMode = true
        ) { ComposeDashboardCountryCard() }
    }


    @Test
    fun allCards_HugeFont() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, fontScale = FontScale.TWO_TO_ONE
        ) { ComposeDashboardCountryCard() }
    }

    @Test
    @Config(qualifiers = "+night")
    fun allCards_HugeFont_DarkMode() {
        composeRule.setContentAndCapture(
            screenshotName = screenshotName, fontScale = FontScale.TWO_TO_ONE, darkMode = true
        ) { ComposeDashboardCountryCard() }
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
