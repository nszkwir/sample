package com.spitzer.data.repository

import com.spitzer.common.database.TransactionState
import com.spitzer.model.data.CountryModel
import kotlinx.coroutines.flow.Flow

interface CountriesRepository {
    val countries: Flow<Map<String, CountryModel>>
    suspend fun getCountry(cca3: String): CountryModel?
    suspend fun fetchCountriesFromRemote()
    suspend fun updateCountry(country: CountryModel)
    suspend fun restoreCountries(): Flow<TransactionState>
}
