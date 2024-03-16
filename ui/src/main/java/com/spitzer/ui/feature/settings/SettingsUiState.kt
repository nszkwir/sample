package com.spitzer.ui.feature.settings

import com.spitzer.ui.components.loadingButton.LoadingButtonState

data class SettingsUiState(
    val restoreButtonState: LoadingButtonState = LoadingButtonState.IDLE,
)
