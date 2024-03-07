package com.spitzer.data.repository

import com.spitzer.common.database.TransactionState
import com.spitzer.model.data.CountryModel
import kotlinx.coroutines.flow.Flow

interface CountriesRepository {
    val countries: Flow<Map<String, CountryModel>>
    val countriesData: Flow<List<CountryModel>>
    suspend fun updateCountries()
    suspend fun upsertCountry(country: CountryModel)

    suspend fun fetchCountriesFromRemote()
    suspend fun updateCountry(country: CountryModel)

    suspend fun restoreCountries(): Flow<TransactionState>
}
