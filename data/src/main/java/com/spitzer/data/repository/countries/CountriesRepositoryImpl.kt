package com.spitzer.data.repository.countries

import com.spitzer.common.database.TransactionState
import com.spitzer.common.network.AppDispatchers
import com.spitzer.common.network.Dispatcher
import com.spitzer.data.mapper.CountryModelMapper
import com.spitzer.data.repository.CountriesRepository
import com.spitzer.database.dao.CountryDao
import com.spitzer.database.dao.FakeRemoteCountryDao
import com.spitzer.model.data.CountryModel
import com.spitzer.network.CountriesNetworkDatasource
import com.spitzer.network.com.spitzer.network.mapper.CountryNetworkModelMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CountriesRepositoryImpl @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val remote: FakeRemoteCountryDao,
    private val database: CountryDao,
    private val countryModelMapper: CountryModelMapper,
    private val restoreDatasource: CountriesNetworkDatasource,
    private val restoreDatasourceModelMapper: CountryNetworkModelMapper
) : CountriesRepository {

    private val _countries = MutableStateFlow<Map<String, CountryModel>>(emptyMap())
    override val countries: Flow<Map<String, CountryModel>> get() = _countries

    init {
        CoroutineScope(SupervisorJob() + ioDispatcher).launch {
            try {
                database.getCountries().collect { countries ->
                    _countries.value = countryModelMapper.mapEntitiesToDataModelMap(countries)
                }
            } catch (e: Exception) {
                // do nothing
            }
        }
    }

    override suspend fun getCountry(cca3: String): CountryModel? {
        return _countries.value[cca3]
    }

    override suspend fun fetchCountriesFromRemote() {
        withContext(ioDispatcher) {
            try {
                remote.getCountries().collect { remoteList ->
                    database.upsertCountries(
                        countryModelMapper.mapFakeRemoteEntityListToEntities(remoteList)
                    )
                    _countries.value =
                        countryModelMapper.mapFakeRemoteEntitiesToDataModelMap(remoteList)
                }
            } catch (e: Exception) {
                // do nothing
            }
        }
    }

    override suspend fun updateCountry(country: CountryModel) {
        withContext(ioDispatcher) {
            try {
                remote.upsertCountry(
                    countryModelMapper.mapDataModelToFakeRemoteEntity(country)
                )
                database.upsertCountry(
                    countryModelMapper.mapDataModelToEntity(country)
                )
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
    override suspend fun restoreCountries() = callbackFlow {
        try {
            trySend(TransactionState.IN_PROGRESS)
            val countries = restoreDatasourceModelMapper.mapNetworkListToDataModelList(
                restoreDatasource.getCountries()
            )
            remote.deleteAllAndInsert(
                countryModelMapper.mapDataModelListToFakeRemoteEntities(countries)
            )
            database.deleteAllCountries()
            trySend(TransactionState.SUCCESS)
        } catch (e: Exception) { // TODO: identify room's specific exception types
            // If remote fails, we don't remove local data
            trySend(TransactionState.ERROR)
        } finally {
            trySend(TransactionState.IDLE)
        }
        awaitClose { }
    }.flowOn(ioDispatcher)
}
