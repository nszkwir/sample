package com.spitzer.feature.countries

sealed interface CountriesUiState {

    data object Loading : CountriesUiState

    data class Success(
        val countries: List<com.spitzer.model.data.CountryModel>,
    ) : CountriesUiState

    data object Error : CountriesUiState
}
