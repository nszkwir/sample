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
                    countriesRepository.countries.asResult().collect { result ->
                        val (loading, error, countries) = when (result) {
                            is Result.Success -> {
                                val searchTextLowercase = searchText.lowercase()
                                val countries = result.data.mapNotNull {
                                    if (it.value.tags.contains(searchTextLowercase)) it.value.asDashboardCountryModel()
                                    else null
                                }
                                Triple(false, false, countries)
                            }

                            is Result.Loading -> Triple(true, false, null)
                            is Result.Error -> Triple(false, true, null)
                        }
                        _uiState.update {
                            it.copy(
                                searchingCountriesProgress = loading,
                                searchingCountriesError = error,
                                countries = countries
                            )
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
