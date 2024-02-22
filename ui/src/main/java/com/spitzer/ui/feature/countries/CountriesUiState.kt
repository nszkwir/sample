package com.spitzer.ui.feature.countries

import com.spitzer.model.data.CountryDataModel

sealed interface CountriesUiState {

    data object Loading : CountriesUiState

    data class Success(
        val countries: List<com.spitzer.model.data.CountryDataModel>,
    ) : CountriesUiState

    data object Error : CountriesUiState
}
