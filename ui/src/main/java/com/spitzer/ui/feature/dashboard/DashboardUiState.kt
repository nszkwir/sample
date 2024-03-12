package com.spitzer.ui.feature.dashboard

import com.spitzer.model.data.CountryModel

data class DashboardUiState(
    val searchText: String? = null,
    val countries: List<DashboardCountryModel>? = null,
    val searchingCountriesProgress: Boolean = false,
    val searchingCountriesError: Boolean = false,
    val searchIsActive: Boolean = false,

    // TODO this is related to the main loading phase
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)

sealed interface DashboardCountriesUiState {
    data object Loading : DashboardCountriesUiState
    data class Success(
        val countries: List<DashboardCountryModel>,
    ) : DashboardCountriesUiState

    data object Error : DashboardCountriesUiState
}

data class DashboardCountryModel(
    val cca3: String,
    val name: String,
    val capital: String,
    val flagUrl: String?
)

fun CountryModel.asDashboardCountryModel(): DashboardCountryModel? {
    return if (this.cca3.isEmpty() || this.name.common.isEmpty() || this.capital.isEmpty()) {
        null
    } else {
        DashboardCountryModel(
            this.cca3,
            this.name.common,
            this.capital.firstOrNull() ?: "",
            this.flags.svg
        )
    }
}
