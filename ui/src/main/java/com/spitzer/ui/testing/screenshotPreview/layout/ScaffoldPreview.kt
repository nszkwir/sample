package com.spitzer.ui.testing.screenshotPreview.layout

import androidx.compose.runtime.Composable
import com.spitzer.model.testing.TestCountryModelProvider
import com.spitzer.ui.R
import com.spitzer.ui.layout.CountryListLayout
import com.spitzer.ui.layout.scaffold.FABConfiguration
import com.spitzer.ui.layout.scaffold.FABLayout
import com.spitzer.ui.layout.scaffold.ScaffoldLayout
import com.spitzer.ui.layout.scaffold.topappbar.LargeTopAppBar
import com.spitzer.ui.layout.scaffold.topappbar.LargeTopAppBarConfiguration
import com.spitzer.ui.layout.scaffold.topappbar.TopAppBar
import com.spitzer.ui.layout.scaffold.topappbar.TopAppBarConfiguration
import com.spitzer.ui.theme.SampleTheme

@Composable
fun ScaffoldLayout_ScreenshotTest() {
    SampleTheme {
        ScaffoldLayout(
            topBarContent = {
                TopAppBar(configuration = TopAppBarConfiguration(
                    title = "TopAppBar",
                    buttonIconId = R.drawable.baseline_settings_24,
                    onButtonClicked = {},
                    navIconId = R.drawable.baseline_arrow_back_24,
                    onNavIconClicked = {}
                ))
            },
            fabContent = {
                FABLayout(configuration = FABConfiguration(
                    fabIconId = R.drawable.baseline_close_24,
                    onFABClicked = {}
                ))
            },
            isLoading = true
        ) {
            CountryListLayout(
                countries = listOf(TestCountryModelProvider.getTestCountryModel()),
                onCountryClicked = {},
                refreshCountryList = {}
            )
        }
    }
}

@Composable
fun ScaffoldLayout_ScreenshotTest_LargeTAB() {
    SampleTheme {
        ScaffoldLayout(
            topBarContent = {
                LargeTopAppBar(configuration = LargeTopAppBarConfiguration(
                    title = "TopAppBar",
                    buttonIconId = R.drawable.baseline_settings_24,
                    onButtonClicked = {},
                    navIconId = R.drawable.baseline_arrow_back_24,
                    onNavIconClicked = {}
                ))
            },
            fabContent = {
                FABLayout(configuration = FABConfiguration(
                    fabIconId = R.drawable.baseline_close_24,
                    onFABClicked = {}
                ))
            },
            isLoading = true
        ) {
            CountryListLayout(
                countries = listOf(TestCountryModelProvider.getTestCountryModel()),
                onCountryClicked = {},
                refreshCountryList = {}
            )
        }
    }
}