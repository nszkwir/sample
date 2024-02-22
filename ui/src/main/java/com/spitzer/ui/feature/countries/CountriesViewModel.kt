package com.spitzer.ui.feature.countries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spitzer.common.result.Result
import com.spitzer.common.result.asResult
import com.spitzer.data.repository.CountriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    countriesRepository: CountriesRepository
) : ViewModel() {

    val countriesState: StateFlow<CountriesUiState> =
        countriesRepository.countriesData
            .asResult()
            .map { result ->
                when (result) {
                    is Result.Success -> {
                        CountriesUiState.Success(result.data)
                    }

                    is Result.Loading -> CountriesUiState.Loading
                    is Result.Error -> CountriesUiState.Error
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = CountriesUiState.Loading,
            )

}
