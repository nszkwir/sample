package com.spitzer.ui.feature.dashboard

import com.spitzer.model.data.CountryModel

data class DashboardUiState(
    val searchText: String? = null,
    val countries: List<DashboardCountryModel> = emptyList(),
    val showDashboardCards: Boolean = true,
    val showCountryList: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)

data class DashboardCountryModel(
    val cca3: String,
    val name: String,
    val capital: String,
    val flagUrl: String?
)

fun CountryModel.asDashboardCountryModel(): DashboardCountryModel? {
    return if (this.cca3.isNullOrEmpty() || this.name.common.isNullOrEmpty() || this.capital.isNullOrEmpty()) {
        null
    } else {
        DashboardCountryModel(this.cca3, this.name.common, this.capital, this.flag?.svg)
    }
}
