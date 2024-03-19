package com.spitzer.sample.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.spitzer.sample.MainActivity
import com.spitzer.sample.configuration.stringResource
import com.spitzer.ui.R
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(application = HiltTestApplication::class, sdk = [33])
@LooperMode(LooperMode.Mode.PAUSED)
@HiltAndroidTest
class SampleNavigationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @BindValue
    @get:Rule(order = 1)
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @get:Rule(order = 2)
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val settings by composeRule.stringResource(R.string.settings)
    private val welcome by composeRule.stringResource(R.string.welcome)
    private val flagsAndCoatOfArms by composeRule.stringResource(R.string.flagsAndCoatOfArms)
    private val fullInformation by composeRule.stringResource(R.string.fullInformation)
    private val statistics by composeRule.stringResource(R.string.statistics)
    private val back by composeRule.stringResource(R.string.back_CD)
    private val button_CD_Idle by composeRule.stringResource(R.string.restoreCountriesData_CD)
    private val addCountry by composeRule.stringResource(R.string.addCountry_CD)

    @Before
    fun setup() = hiltRule.inject()

    @Test
    fun DashboardScreen_NavigateTo_SettingsScreen() {
        composeRule.apply {
            onNodeWithText(welcome).assertIsDisplayed()
            onNodeWithContentDescription(flagsAndCoatOfArms).assertIsDisplayed()
            onNodeWithContentDescription(statistics).assertIsDisplayed()
            onNodeWithContentDescription(fullInformation).assertIsDisplayed()
            onNodeWithContentDescription(settings).assertIsDisplayed().performClick()
            onNodeWithText(settings).assertIsDisplayed()
            onNodeWithContentDescription(back).assertIsDisplayed()
            onNodeWithContentDescription(button_CD_Idle).assertIsDisplayed()
        }
    }

    @Test
    fun SettingsScreen_NavigateBackTo_DashboardScreen() {
        composeRule.apply {
            onNodeWithContentDescription(settings).assertIsDisplayed().performClick()
            onNodeWithText(settings).assertExists()
            onNodeWithContentDescription(button_CD_Idle).assertExists()
            onNodeWithContentDescription(back).assertIsDisplayed().performClick()
            onNodeWithText(welcome).assertExists()
            onNodeWithContentDescription(flagsAndCoatOfArms).assertExists()
            onNodeWithContentDescription(statistics).assertExists()
            onNodeWithContentDescription(fullInformation).assertExists()
        }
    }

    @Test
    fun DashboardScreen_NavigateTo_CountriesScreen() {
        composeRule.apply {
            onNodeWithText(welcome).assertIsDisplayed()
            onNodeWithContentDescription(statistics).assertIsDisplayed()
            onNodeWithContentDescription(fullInformation).assertIsDisplayed()
            onNodeWithContentDescription(flagsAndCoatOfArms).assertIsDisplayed().performClick()
            onNodeWithContentDescription(back).assertIsDisplayed()
            onNodeWithContentDescription(addCountry).assertIsDisplayed()
        }
    }

    @Test
    fun CountriesScreen_NavigateBackTo_DashboardScreen() {
        composeRule.apply {
            onNodeWithContentDescription(flagsAndCoatOfArms).assertIsDisplayed().performClick()
            onNodeWithContentDescription(addCountry).assertIsDisplayed()
            onNodeWithContentDescription(back).assertIsDisplayed().performClick()
            onNodeWithText(welcome).assertIsDisplayed()
            onNodeWithContentDescription(statistics).assertIsDisplayed()
            onNodeWithContentDescription(fullInformation).assertIsDisplayed()
        }
    }
}
