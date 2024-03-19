package com.spitzer.data.fake

import com.spitzer.common.database.TransactionState
import com.spitzer.common.network.AppDispatchers
import com.spitzer.common.network.Dispatcher
import com.spitzer.data.mapper.CountryModelMapper
import com.spitzer.data.repository.CountriesRepository
import com.spitzer.database.dao.CountryDao
import com.spitzer.database.dao.FakeRemoteCountryDao
import com.spitzer.model.data.CountryModel
import com.spitzer.model.testing.TestCountryModelProvider
import com.spitzer.network.CountriesNetworkDatasource
import com.spitzer.network.com.spitzer.network.mapper.CountryNetworkModelMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FakeCountriesRepository @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : CountriesRepository {

    private val _countries = MutableStateFlow<Map<String, CountryModel>>(emptyMap())
    override val countries: Flow<Map<String, CountryModel>> get() = _countries

    private var remoteCountries: Map<String, CountryModel> = emptyMap()

    override suspend fun getCountry(cca3: String): CountryModel? {
        return _countries.value[cca3]
    }

    override suspend fun fetchCountriesFromRemote() {
        withContext(ioDispatcher) {
            _countries.update { remoteCountries }
        }
    }

    override suspend fun updateCountry(country: CountryModel) {
        withContext(ioDispatcher) {
            try {
                _countries.value = _countries.value.toMutableMap().apply {
                    put(country.cca3, country)
                }
            } catch (e: Exception) {
                // do nothing
            }
        }
    }

    /**
     *  Util function to clean local DB and set remote with the initial Data from restoreDataSource
     */
    override suspend fun restoreCountriesFlow() = flow {
        val fakeRemoteCountries = listOf(
            TestCountryModelProvider.getTestCountryModel(
                cca3 = "ARG",
                name = "Argentina",
                tags = "argentina"
            ),
            TestCountryModelProvider.getTestCountryModel(
                cca3 = "URY",
                name = "Uruguay",
                tags = "uruguay"
            ),
            TestCountryModelProvider.getTestCountryModel(
                cca3 = "BRA",
                name = "Brasil",
                tags = "brasil"
            ),
        )
        remoteCountries = fakeRemoteCountries.associateBy({ it.cca3 }, { it })
        _countries.update { emptyMap() }
        emit(TransactionState.IN_PROGRESS)
    }.flowOn(ioDispatcher)
}
