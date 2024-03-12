package com.spitzer.ui.feature

import com.spitzer.common.database.TransactionState
import com.spitzer.data.repository.CountriesRepository
import com.spitzer.model.data.CountryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FakeTestCountriesRepository(
) : CountriesRepository {
    private val _countries = MutableSharedFlow<Map<String, CountryModel>>()
    override var countries: Flow<Map<String, CountryModel>> = _countries

    private var auxCountriesMap: Map<String, CountryModel> = emptyMap()

    fun setCountriesMap(value: Map<String, CountryModel>) {
        auxCountriesMap = value
    }

    fun emitException(exceptionMessage: String) {
        countries = flow { throw RuntimeException(exceptionMessage) }
    }

    suspend fun updateCountriesMap(value: Map<String, CountryModel>) {
        _countries.emit(value)
    }

    private var auxCountry: CountryModel? = null
    fun setCountry(country: CountryModel) {
        auxCountry = country
    }

    override suspend fun getCountry(cca3: String): CountryModel? {
        return auxCountry
    }

    private var remoteMap: Map<String, CountryModel> = emptyMap()
    fun setRemoteMap(map: Map<String, CountryModel>) {
        remoteMap = map
    }

    override suspend fun fetchCountriesFromRemote() {
        _countries.emit(remoteMap)
    }

    override suspend fun updateCountry(country: CountryModel) {
        _countries.emit(auxCountriesMap.toMutableMap().apply { put(country.cca3, country) })
    }

    // TODO define behavior if needed for testing
    override suspend fun restoreCountriesFlow() = flowOf<TransactionState>()

}
