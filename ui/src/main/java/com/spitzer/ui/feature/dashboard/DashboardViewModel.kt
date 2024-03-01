package com.spitzer.ui.feature.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spitzer.common.result.Result
import com.spitzer.common.result.asResult
import com.spitzer.data.repository.CountriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val countriesRepository: CountriesRepository
) : ViewModel() {

    private var searchJob: Job? = null

    private val _uiState: MutableStateFlow<DashboardUiState> = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState

    fun onSearchTextChange(searchText: String) {
        val searchIsActive = searchText.length > 1
        _uiState.update {
            it.copy(
                searchText = searchText,
                searchIsActive = searchIsActive,
            )
        }
        if (searchIsActive) {
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                try {
                    countriesRepository.countriesData.asResult().collect { result ->
                        val countriesUiState = when (result) {
                            is Result.Success -> {
                                val searchTextLowercase = searchText.lowercase()
                                DashboardCountriesUiState.Success(
                                    result.data.mapNotNull {
                                        if (it.capital.lowercase()
                                                .contains(searchTextLowercase) || it.name.common.lowercase()
                                                .contains(searchTextLowercase)
                                        ) it.asDashboardCountryModel()
                                        else null
                                    }
                                )
                            }

                            is Result.Loading -> DashboardCountriesUiState.Loading
                            is Result.Error -> DashboardCountriesUiState.Error
                        }
                        _uiState.update {
                            it.copy(countriesUiState = countriesUiState)
                        }
                    }
                } finally {
                    searchJob = null
                }
            }
        }
    }

    fun clearSearchText() {
        onSearchTextChange("")
    }
}
