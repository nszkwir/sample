package com.spitzer.ui.feature.countries

import com.spitzer.model.data.CountryModel

sealed interface CountriesUiState {

    data object Loading : CountriesUiState

    data class Success(
        val countries: Map<String, CountryModel>,
    ) : CountriesUiState

    data object Error : CountriesUiState
}
