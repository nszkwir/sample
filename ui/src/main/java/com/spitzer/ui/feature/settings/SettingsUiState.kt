package com.spitzer.ui.feature.settings

sealed interface SettingsUiState {

    data object Loading : SettingsUiState

    data class Success(
        val countries: List<com.spitzer.model.data.CountryModel>,
    ) : SettingsUiState

    data object Error : SettingsUiState
}
