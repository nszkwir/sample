package com.spitzer.ui.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spitzer.data.repository.FakeCountriesRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val fakeCountriesRepository: FakeCountriesRemoteRepository
) : ViewModel() {

    fun updateCountries() = viewModelScope.launch {
        fakeCountriesRepository.updateCountries()
    }
}
