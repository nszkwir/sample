package com.spitzer.ui.feature.dashboard

import androidx.lifecycle.ViewModel
import com.spitzer.data.repository.FakeCountriesRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val fakeCountriesRepository: FakeCountriesRemoteRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<DashboardUiState> = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState

    fun onSearchTextChange(searchText: String) {
        val showCountryList = searchText.length > 2
        _uiState.update {
            it.copy(searchText = searchText, showCountryList = showCountryList)
        }
    }

    fun clearSearchText() {
        onSearchTextChange("")
    }
}
