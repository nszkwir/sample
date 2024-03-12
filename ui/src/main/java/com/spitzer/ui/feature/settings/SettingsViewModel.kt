package com.spitzer.ui.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spitzer.data.repository.CountriesRepository
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
    private val countriesRepository: CountriesRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<SettingsUiState> = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState

    fun updateCountries() = viewModelScope.launch {
        if (_uiState.value.restoreButtonState == LoadingButtonState.IDLE)
            countriesRepository.restoreCountriesFlow()
                .collect { state ->
                    val buttonState = state.toLoadingButtonState()
                    // Adding delays to emulate network behavior
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
