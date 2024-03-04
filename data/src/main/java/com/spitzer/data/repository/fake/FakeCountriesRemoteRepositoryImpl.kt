package com.spitzer.data.repository.fake

import com.spitzer.common.database.TransactionState
import com.spitzer.common.network.AppDispatchers
import com.spitzer.common.network.Dispatcher
import com.spitzer.data.mapper.asFakeRemoteCountryEntity
import com.spitzer.data.repository.FakeCountriesRemoteRepository
import com.spitzer.database.dao.CountryDao
import com.spitzer.database.dao.FakeRemoteCountryDao
import com.spitzer.network.CountriesNetworkDatasource
import com.spitzer.network.com.spitzer.network.mapper.CountryNetworkModelMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FakeCountriesRemoteRepositoryImpl @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkDatasource: CountriesNetworkDatasource,
    private val remoteCountryDao: FakeRemoteCountryDao,
    private val countryDao: CountryDao,
    private val countryNetworkModelMapper: CountryNetworkModelMapper
) : FakeCountriesRemoteRepository {

    override suspend fun updateCountries() = callbackFlow {
        trySend(TransactionState.IN_PROGRESS)
        val countries = networkDatasource.getCountries()
            .mapNotNull(countryNetworkModelMapper::mapToModel)
        try {
            remoteCountryDao.deleteAllAndInsert(countries.map { it.asFakeRemoteCountryEntity() })
            countryDao.deleteAllCountries()
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
