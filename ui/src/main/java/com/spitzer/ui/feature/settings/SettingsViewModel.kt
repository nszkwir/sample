package com.spitzer.ui.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spitzer.data.repository.FakeCountriesRemoteRepository
import com.spitzer.ui.components.loadingButton.LoadingButtonState
import com.spitzer.ui.components.loadingButton.toLoadingButtonState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val fakeCountriesRepository: FakeCountriesRemoteRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<SettingsUiState> = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState

    fun updateCountries() = viewModelScope.launch {
        if (_uiState.value.restoreButtonState == LoadingButtonState.IDLE)
            fakeCountriesRepository.updateCountries()
                .collect { state ->
                    val buttonState = state.toLoadingButtonState()
                    when (buttonState) {
                        LoadingButtonState.ERROR, LoadingButtonState.SUCCESS -> delay(3000)
                        LoadingButtonState.IDLE -> delay(2000)
                        else -> delay(100)
                    }
                    _uiState.update {
                        it.copy(restoreButtonState = buttonState)
                    }
                }
    }
}
