package com.spitzer.ui.feature.countries

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.spitzer.data.model.CountryDataModel
import com.spitzer.ui.layout.CountryListLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun CountriesScreen(
    viewModel: CountriesViewModel = hiltViewModel(),
    fabButtonClicked: () -> Unit,
    onCountryClicked: () -> Unit
) {
    val uiState by viewModel.countriesState.collectAsStateWithLifecycle()

    val countries = when(uiState) {
        is CountriesUiState.Success -> (uiState as CountriesUiState.Success).countries
        else -> emptyList()
    }

    CountryListLayout(countries = countries)
}
