package com.spitzer.sample.integration

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipe
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.spitzer.sample.MainActivity
import com.spitzer.sample.configuration.captureRoborazziImage
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
class IntegrationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @BindValue
    @get:Rule(order = 1)
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @get:Rule(order = 2)
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val settings by composeRule.stringResource(R.string.settings)
    private val flagsAndCoatOfArms by composeRule.stringResource(R.string.flagsAndCoatOfArms)
    private val back by composeRule.stringResource(R.string.back_CD)
    private val addCountry by composeRule.stringResource(R.string.addCountry_CD)

    private val restoreCountriesData_TT by composeRule.stringResource(R.string.RestoreCountriesDataButton_TT)
    private val CountryListBoxPullToRefreshContainer_TT by composeRule.stringResource(R.string.CountryListBoxPullToRefreshContainer_TT)
    private val countrySearchField_TT by composeRule.stringResource(R.string.CountrySearchField_TT)
    private val emptyDashboardCard_TT by composeRule.stringResource(R.string.EmptyDashboardCard_TT)

    @Before
    fun setup() = hiltRule.inject()

    @Test
    fun `Searching country in DashboardScreen without data loaded, country NotFound`() {
        composeRule.apply {
            onNodeWithTag(countrySearchField_TT).performTextInput("Arg")
            waitForIdle()
            onNodeWithText("Argentina").assertDoesNotExist()
            onNodeWithTag(emptyDashboardCard_TT).assertIsDisplayed()
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun `Load data from settings and then Searching country, country Found`() {
        composeRule.apply {
            onNodeWithContentDescription(settings).assertIsDisplayed().performClick()
            onNodeWithTag(restoreCountriesData_TT, true).assertIsDisplayed().performClick()
            waitForIdle()
            onNodeWithContentDescription(back).assertIsDisplayed().performClick()
            onNodeWithContentDescription(flagsAndCoatOfArms).assertIsDisplayed().performClick()
            onNodeWithContentDescription(addCountry).assertIsDisplayed()
            onNodeWithTag(CountryListBoxPullToRefreshContainer_TT)
                .assertIsDisplayed()
                .performTouchInput {
                    swipe(
                        start = center,
                        end = center + Offset(x = 0f, y = 400f),
                        durationMillis = 1000,
                    )
                }
            waitUntilAtLeastOneExists(hasText("Argentina"), timeoutMillis = 2000)
            onNodeWithContentDescription(back).assertIsDisplayed().performClick()
            onNodeWithTag(countrySearchField_TT).performTextInput("Arg")
            waitForIdle()
            onNodeWithText("Argentina").assertIsDisplayed()
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Config(qualifiers = RobolectricDeviceQualifiers.Pixel7)
    @Test
    fun `Loading data from Settings, then going to CountryDetails to Refresh data, and finally to Country Details`() {
        val screenshotName = "Integration1/complete_flow"
        composeRule.apply {
            onRoot().captureRoborazziImage("${screenshotName}_1_Dashboard")
            onNodeWithContentDescription(settings).assertIsDisplayed().performClick()
            waitForIdle()
            onNodeWithTag(restoreCountriesData_TT, true).assertIsDisplayed().performClick()
            waitForIdle()
            onRoot().captureRoborazziImage("${screenshotName}_2_Settings")
            onNodeWithContentDescription(back).assertIsDisplayed().performClick()
            waitForIdle()
            onRoot().captureRoborazziImage("${screenshotName}_3_Dashboard")
            onNodeWithContentDescription(flagsAndCoatOfArms).assertIsDisplayed().performClick()
            waitForIdle()
            onRoot().captureRoborazziImage("${screenshotName}_4_CountryList")
            onNodeWithContentDescription(addCountry).assertIsDisplayed()
            onNodeWithTag(CountryListBoxPullToRefreshContainer_TT)
                .assertIsDisplayed()
                .performTouchInput {
                    swipe(
                        start = center,
                        end = center + Offset(x = 0f, y = 800f),
                        durationMillis = 10,
                    )
                }
            waitForIdle()
            waitUntilAtLeastOneExists(hasText("Argentina"), timeoutMillis = 2000)
            onRoot().captureRoborazziImage("${screenshotName}_5_CountryList_AfterRefresh")
            onNodeWithContentDescription("Argentina").assertIsDisplayed().performClick()
            waitForIdle()
            onRoot().captureRoborazziImage("${screenshotName}_6_CountryDetails")
        }
    }
}
