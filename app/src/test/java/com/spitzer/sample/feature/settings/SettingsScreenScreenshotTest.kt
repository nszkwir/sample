package com.spitzer.sample.feature.settings

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.spitzer.sample.configuration.captureMultiDeviceMultiMode
import com.spitzer.ui.components.loadingButton.LoadingButtonState
import com.spitzer.ui.feature.settings.SettingsScreen
import com.spitzer.ui.feature.settings.SettingsUiState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(
    sdk = [33]
)
class SettingsScreenScreenshotTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()
    private val screenshotName = "Feature/Settings/"
    private val idleScreenshotName = "${screenshotName}idle"
    private val loadingScreenshotName = "${screenshotName}loading"
    private val errorScreenshotName = "${screenshotName}error"
    private val successScreenshotName = "${screenshotName}success"

    @Test
    fun SettingsScreen_Idle() {
        composeRule.captureMultiDeviceMultiMode(screenshotName = idleScreenshotName) {
            SettingsScreen(uiState = SettingsUiState())
        }
    }

    @Test
    fun SettingsScreen_Loading() {
        composeRule.captureMultiDeviceMultiMode(screenshotName = loadingScreenshotName) {
            SettingsScreen(uiState = SettingsUiState(restoreButtonState = LoadingButtonState.IN_PROGRESS))
        }
    }

    @Test
    fun SettingsScreen_Error() {
        composeRule.captureMultiDeviceMultiMode(screenshotName = errorScreenshotName) {
            SettingsScreen(uiState = SettingsUiState(restoreButtonState = LoadingButtonState.ERROR))
        }
    }

    @Test
    fun SettingsScreen_Success() {
        composeRule.captureMultiDeviceMultiMode(screenshotName = successScreenshotName) {
            SettingsScreen(uiState = SettingsUiState(restoreButtonState = LoadingButtonState.SUCCESS))
        }
    }
}
