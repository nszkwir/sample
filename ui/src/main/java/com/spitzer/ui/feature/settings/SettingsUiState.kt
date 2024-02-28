package com.spitzer.ui.feature.settings

import com.spitzer.ui.components.loadingButton.LoadingButtonState

data class SettingsUiState(

    val remoteCountriesAmount: Int = 0,
    val countriesAmount: Int = 0,
    val restoreButtonState: LoadingButtonState = LoadingButtonState.IDLE,

    val isLoading: Boolean = false,
    val isError: Boolean = false,
)
