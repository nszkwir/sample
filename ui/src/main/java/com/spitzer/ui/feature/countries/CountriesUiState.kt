package com.spitzer.ui.feature.countries

import com.spitzer.data.model.CountryDataModel

sealed interface CountriesUiState {

    data object Loading : CountriesUiState

    data class Success(
        val countries: List<CountryDataModel>,
    ) : CountriesUiState

    data object Error : CountriesUiState
}
