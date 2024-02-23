package com.spitzer.data.repository

import com.spitzer.model.data.CountryModel
import kotlinx.coroutines.flow.Flow

interface CountriesRepository {
    val countriesData: Flow<List<CountryModel>>
    suspend fun updateCountries()
    suspend fun upsertCountry(country: CountryModel)
}
