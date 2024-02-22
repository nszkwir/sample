package com.spitzer.data.repository

import com.spitzer.model.data.CountryDataModel
import kotlinx.coroutines.flow.Flow

interface CountriesRepository {
    val countriesData: Flow<List<CountryDataModel>>
    suspend fun updateCountries()
    suspend fun upsertCountry(country: CountryDataModel)
}
