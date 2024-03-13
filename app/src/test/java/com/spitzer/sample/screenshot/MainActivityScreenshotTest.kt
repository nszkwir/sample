package com.spitzer.sample.screenshot

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.spitzer.sample.MainActivity
import com.spitzer.sample.configuration.DefaultTestDevices
import com.spitzer.sample.configuration.captureForDevice
import com.spitzer.sample.configuration.captureMultiDevice
import com.spitzer.ui.feature.dashboard.DashboardScreen
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
)
class MainActivityScreenshotTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val screenshotName = "MainActivity/appStartup"

    @Test
    fun appStartup() {
        composeRule.captureForDevice(
            deviceName = DefaultTestDevices.SMALLPHONE.description,
            deviceSpec = DefaultTestDevices.SMALLPHONE.spec,
            screenshotName = screenshotName,
            darkMode = false,
        ) {
            SampleTheme {
                DashboardScreen(
                    onTopAppBarIconClicked = {},
                    onTopAppBarNavIconClicked = { },
                    onNavigateToFullCountryList = {},
                    onCountryClicked = {}
                )
            }
        }
    }

    @Test
    fun appStartup_MultiDevice() {
        composeRule.captureMultiDevice(
            screenshotName = screenshotName
        ) {
            SampleTheme {
                DashboardScreen(
                    onTopAppBarIconClicked = {},
                    onTopAppBarNavIconClicked = { },
                    onNavigateToFullCountryList = {},
                    onCountryClicked = {}
                )
            }
        }
    }

    @Test
    @Config(qualifiers = "+night")
    fun appStartup_MultiDevice_DarkMode() {
        composeRule.captureMultiDevice(
            screenshotName = screenshotName,
            darkMode = true
        ) {
            SampleTheme {
                DashboardScreen(
                    onTopAppBarIconClicked = {},
                    onTopAppBarNavIconClicked = { },
                    onNavigateToFullCountryList = {},
                    onCountryClicked = {}
                )
            }
        }
    }
}
