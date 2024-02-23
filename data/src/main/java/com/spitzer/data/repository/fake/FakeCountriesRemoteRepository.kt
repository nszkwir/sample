package com.spitzer.data.repository.fake

import com.spitzer.common.network.AppDispatchers
import com.spitzer.common.network.Dispatcher
import com.spitzer.data.mapper.asFakeRemoteCountryEntity
import com.spitzer.data.repository.CountriesRepository
import com.spitzer.database.dao.CountryDao
import com.spitzer.database.dao.FakeRemoteCountryDao
import com.spitzer.model.data.CountryModel
import com.spitzer.network.CountriesNetworkDatasource
import com.spitzer.network.model.CountryInfoNetworkModel
import com.spitzer.network.model.asDataModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FakeCountriesRemoteRepository @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkDatasource: CountriesNetworkDatasource,
    private val remoteCountryDao: FakeRemoteCountryDao,
    private val countryDao: CountryDao
) : CountriesRepository {

    override val countriesData: Flow<List<CountryModel>> = flow { emit(emptyList()) }

    override suspend fun updateCountries() {
        withContext(ioDispatcher) {
            val countries = networkDatasource.getCountriesInfo()
                .mapNotNull(CountryInfoNetworkModel::asDataModel)

            countryDao.deleteAllCountries()
            remoteCountryDao.deleteAllCountries()
            remoteCountryDao.upsertCountries(countries.map { it.asFakeRemoteCountryEntity() })

        }
    }

    override suspend fun upsertCountry(country: CountryModel) {
        // not being used
    }
}
