package com.spitzer.data.repository.countries

import com.spitzer.common.database.TransactionState
import com.spitzer.common.network.AppDispatchers
import com.spitzer.common.network.Dispatcher
import com.spitzer.data.mapper.asCountryEntity
import com.spitzer.data.mapper.asFakeRemoteCountryEntity
import com.spitzer.data.repository.CountriesRepository
import com.spitzer.database.dao.CountryDao
import com.spitzer.database.dao.FakeRemoteCountryDao
import com.spitzer.database.model.FakeRemoteCountryEntity
import com.spitzer.database.model.asCountryEntity
import com.spitzer.database.model.asDataModel
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
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CountriesRepositoryImpl @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val restoreDatasource: CountriesNetworkDatasource,
    private val remote: FakeRemoteCountryDao,
    private val database: CountryDao,
    private val restoreDatasourceModelMapper: CountryNetworkModelMapper
) : CountriesRepository {

    private val _countries = MutableStateFlow<Map<String, CountryModel>>(emptyMap())
    override val countries: Flow<Map<String, CountryModel>> get() = _countries

    init {
        CoroutineScope(SupervisorJob() + ioDispatcher).launch {
            database.getCountries().collect { countries ->
                _countries.value = countries.map { entity ->
                    entity.asDataModel()
                }.associateBy({ it.cca3 }, { it })
            }
        }
    }

    override suspend fun getCountry(cca3: String): CountryModel? {
        return _countries.value[cca3]
    }

    override suspend fun fetchCountriesFromRemote() {
        withContext(ioDispatcher) {
            remote.getCountries().collect { remoteList ->
                database.upsertCountries(remoteList.map(FakeRemoteCountryEntity::asCountryEntity))
                _countries.value = remoteList.map { entity ->
                    entity.asDataModel()
                }.associateBy({ it.cca3 }, { it })
            }
        }
    }

    override suspend fun updateCountry(country: CountryModel) {
        withContext(ioDispatcher) {
            remote.upsertCountry(country.asFakeRemoteCountryEntity())
            database.upsertCountry(country.asCountryEntity())
            _countries.value = _countries.value.toMutableMap().apply {
                put(country.cca3, country)
            }
        }
    }

    override suspend fun restoreCountries() = callbackFlow {
        trySend(TransactionState.IN_PROGRESS)
        val countries = restoreDatasource.getCountries()
            .mapNotNull(restoreDatasourceModelMapper::mapToModel)
        try {
            remote.deleteAllAndInsert(countries.map { it.asFakeRemoteCountryEntity() })
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

    /** BEFORE */
    override val countriesData: Flow<List<CountryModel>> =
        database.getCountries().map {
            it.map { entity ->
                entity.asDataModel()
            }
        }.flowOn(ioDispatcher)

    override suspend fun updateCountries() {
        withContext(ioDispatcher) {
            val remoteCountries = remote.getCountries().firstOrNull()
            remoteCountries?.let {
                database.upsertCountries(it.map(FakeRemoteCountryEntity::asCountryEntity))
            }
        }
    }

    override suspend fun upsertCountry(country: CountryModel) {
        withContext(ioDispatcher) {
            remote.upsertCountry(country.asFakeRemoteCountryEntity())
            database.upsertCountry(country.asCountryEntity())
        }
    }
}
