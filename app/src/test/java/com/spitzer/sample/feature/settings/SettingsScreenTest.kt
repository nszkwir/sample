package com.spitzer.sample.feature.settings

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.spitzer.ui.R
import com.spitzer.ui.components.loadingButton.LoadingButtonState
import com.spitzer.ui.feature.countryDetails.CountryDetailsScreen
import com.spitzer.ui.feature.countryDetails.CountryDetailsUiState
import com.spitzer.ui.feature.settings.SettingsScreen
import com.spitzer.ui.feature.settings.SettingsUiState
import org.junit.Before
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
class SettingsScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var back: String
    private lateinit var settings: String
    private lateinit var restoreCountriesData_CD: String
    private lateinit var button_CD_Idle: String
    private lateinit var button_CD_Loading: String
    private lateinit var button_CD_Error: String
    private lateinit var button_CD_Success: String

    @Before
    fun appStartup() {
        composeRule.activity.apply {
            back = getString(R.string.back_CD)
            settings = getString(R.string.settings)
            restoreCountriesData_CD = getString(R.string.restoreCountriesData_CD)
            button_CD_Idle = restoreCountriesData_CD
            button_CD_Loading = restoreCountriesData_CD + " " + getString(R.string.loading_CD)
            button_CD_Error = restoreCountriesData_CD + " " + getString(R.string.error_CD)
            button_CD_Success = restoreCountriesData_CD + " " + getString(R.string.success_CD)

        }
    }

    @Test
    fun `Loading button state is IDLE`() {
        composeRule.setContent { SettingsScreen(uiState = SettingsUiState()) }
        composeRule.onNodeWithText(settings).assertExists()
        composeRule.onNodeWithContentDescription(back).assertExists()
        composeRule.onNodeWithContentDescription(button_CD_Idle).assertExists()
    }

    @Test
    fun `Loading button state is IN_PROGRESS`() {
        composeRule.setContent { SettingsScreen(uiState = SettingsUiState(restoreButtonState = LoadingButtonState.IN_PROGRESS)) }
        composeRule.onNodeWithText(settings).assertExists()
        composeRule.onNodeWithContentDescription(back).assertExists()
        composeRule.onNodeWithContentDescription(button_CD_Loading).assertExists()
    }

    @Test
    fun `Loading button state is SUCCESS`() {
        composeRule.setContent { SettingsScreen(uiState = SettingsUiState(restoreButtonState = LoadingButtonState.SUCCESS)) }
        composeRule.onNodeWithText(settings).assertExists()
        composeRule.onNodeWithContentDescription(back).assertExists()
        composeRule.onNodeWithContentDescription(button_CD_Success).assertExists()
    }

    @Test
    fun `Loading button state is ERROR`() {
        composeRule.setContent { SettingsScreen(uiState = SettingsUiState(restoreButtonState = LoadingButtonState.ERROR)) }
        composeRule.onNodeWithText(settings).assertExists()
        composeRule.onNodeWithContentDescription(back).assertExists()
        composeRule.onNodeWithContentDescription(button_CD_Error).assertExists()
    }

}
