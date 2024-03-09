package com.spitzer.ui.feature.countryDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spitzer.data.repository.CountriesRepository
import com.spitzer.data.repository.LanguagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryDetailsViewModel @Inject constructor(
    private val countriesRepository: CountriesRepository,
    private val languagesRepository: LanguagesRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<CountryDetailsUiState> =
        MutableStateFlow(CountryDetailsUiState())
    val uiState: StateFlow<CountryDetailsUiState> = _uiState

    fun getCountryData(cca3: String?) {
        if (cca3.isNullOrEmpty()) {
            _uiState.update {
                it.copy(isLoading = false, isError = true)
            }
        } else {
            viewModelScope.launch {
                val languages = languagesRepository.getLanguages()
                val country =
                    countriesRepository.getCountry(cca3)?.mapToCountryDetailsModel(languages)
                if (country == null) {
                    _uiState.update {
                        it.copy(isLoading = false, isError = true)
                    }
                } else {
                    _uiState.update {
                        it.copy(country = country, isLoading = false, isError = false)
                    }
                }
            }
        }
    }
}
