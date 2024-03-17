package com.spitzer.sample.feature.countryDetails

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.spitzer.model.testing.TestCountryModelProvider
import com.spitzer.ui.R
import com.spitzer.ui.feature.countryDetails.CountryDetailsScreen
import com.spitzer.ui.feature.countryDetails.CountryDetailsUiState
import com.spitzer.ui.feature.countryDetails.mapToCountryDetailsModel
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
class CountryDetailsScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var loading: String
    private lateinit var back: String
    private lateinit var emptyEditCountry: String
    private lateinit var editCountry: String
    private val country = TestCountryModelProvider.getTestCountryModel()
    private val detailsCountryModel = country.mapToCountryDetailsModel(emptyMap())

    @Before
    fun appStartup() {
        composeRule.activity.apply {
            loading = getString(R.string.loading)
            back = getString(R.string.back_CD)
            emptyEditCountry = getString(R.string.editCountry_CD, "")
            editCountry = getString(R.string.editCountry_CD, detailsCountryModel.commonName)
        }
    }

    @Test
    fun `When CountryDetailsUiState is Loading, LoadingLayout is shown`() {
        composeRule.setContent { CountryDetailsScreen(uiState = CountryDetailsUiState()) }
        composeRule.onNodeWithContentDescription(loading).assertExists()
        composeRule.onNodeWithContentDescription(back).assertExists()
        composeRule.onNodeWithContentDescription(editCountry).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(emptyEditCountry).assertExists()
    }

    @Test
    fun `When CountryDetailsUiState is Error, ErrorLayout is shown`() {
        // TODO Implement ErrorLayout
        composeRule.setContent {
            CountryDetailsScreen(
                uiState = CountryDetailsUiState(
                    isLoading = false,
                    isError = true
                )
            )
        }
        composeRule.onNodeWithContentDescription(loading).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(back).assertExists()
        composeRule.onNodeWithContentDescription(editCountry).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(emptyEditCountry).assertExists()
    }

    @Test
    fun `When CountryDetailsUiState is Success, CountryDetails is shown`() {
        composeRule.setContent {
            CountryDetailsScreen(
                uiState = CountryDetailsUiState(
                    isLoading = false,
                    isError = false,
                    country = detailsCountryModel
                )
            )
        }
        composeRule.onNodeWithContentDescription(loading).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(back).assertExists()
        composeRule.onNodeWithContentDescription(editCountry).assertExists()
        composeRule.onNodeWithText(detailsCountryModel.commonName).assertExists()
        composeRule.onNodeWithText(detailsCountryModel.officialName ?: "").assertExists()
        // TODO when design is final, add assertions to cover all the displayed screen content
    }
}
