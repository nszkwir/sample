package com.spitzer.sample.feature.countries

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.spitzer.model.testing.TestCountryModelProvider
import com.spitzer.ui.R
import com.spitzer.ui.feature.countries.CountriesScreen
import com.spitzer.ui.feature.countries.CountriesUiState
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
class CountriesScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var loading: String
    private lateinit var back: String
    private lateinit var addCountry: String

    @Before
    fun appStartup() {
        composeRule.activity.apply {
            loading = getString(R.string.loading)
            back = getString(R.string.back_CD)
            addCountry = getString(R.string.addCountry_CD)
        }
    }

    @Test
    fun `When CountriesScrenUiState is Loading, LoadingLayout is shown`() {
        composeRule.setContent { CountriesScreen(uiState = CountriesUiState.Loading) }
        composeRule.onNodeWithContentDescription(loading).assertExists()
        composeRule.onNodeWithContentDescription(back).assertExists()
        composeRule.onNodeWithContentDescription(addCountry).assertExists()

    }

    @Test
    fun `When CountriesScrenUiState is Error, ErrorLayout is shown`() {
        // TODO Implement ErrorLayout
        composeRule.setContent { CountriesScreen(uiState = CountriesUiState.Error) }
        composeRule.onNodeWithContentDescription(loading).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(back).assertExists()
        composeRule.onNodeWithContentDescription(addCountry).assertExists()
    }

    @Test
    fun `When CountriesScrenUiState is Success, CountriesListLayout is shown`() {
        val list = listOf(
            TestCountryModelProvider.getTestCountryModel(cca3 = "ARG", name = "Argentina"),
            TestCountryModelProvider.getTestCountryModel(cca3 = "URY", name = "Uruguay")
        )

        composeRule.setContent {
            CountriesScreen(
                uiState = CountriesUiState.Success(
                    list.associateBy({ it.cca3 }, { it })
                )
            )
        }
        composeRule.onNodeWithContentDescription(loading).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(back).assertExists()
        composeRule.onNodeWithContentDescription(addCountry).assertExists()
        composeRule.onNodeWithContentDescription(list[0].name.common).assertExists()
        composeRule.onNodeWithContentDescription(list[1].name.common).assertExists()
    }
}
