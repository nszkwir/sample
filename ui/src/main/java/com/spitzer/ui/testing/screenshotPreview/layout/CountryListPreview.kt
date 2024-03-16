package com.spitzer.ui.testing.screenshotPreview.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spitzer.model.testing.TestCountryModelProvider
import com.spitzer.ui.layout.CountryListLayout
import com.spitzer.ui.theme.SampleTheme

@Composable
fun CountryListLayout_ScreenshotTest() {
    SampleTheme {
        Column(modifier = Modifier.padding(5.dp)) {
            CountryListLayout(countries =
            listOf(
                TestCountryModelProvider.getTestCountryModel(),
                TestCountryModelProvider.getTestCountryModel("URY", "Uruguay")
            ), onCountryClicked = {}) {

            }
        }
    }
}
