package com.spitzer.data.repository.countries

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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CountriesRepository @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val remote: FakeRemoteCountryDao,
    private val database: CountryDao
) : CountriesRepository {
    override val countriesData: Flow<List<CountryModel>> =
        database.getCountries().map {
            it.map { entity ->
                entity.asDataModel()
            }
        }.flowOn(ioDispatcher)

    override suspend fun updateCountries() {
        withContext(ioDispatcher) {
            remote.getCountries().map { remoteList ->
                database.upsertCountries(
                    remoteList.map(FakeRemoteCountryEntity::asCountryEntity)
                )
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
