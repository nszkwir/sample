package com.spitzer.data.repository.fake

import com.spitzer.common.network.AppDispatchers
import com.spitzer.common.network.Dispatcher
import com.spitzer.data.repository.CountriesRepository
import com.spitzer.model.data.CountryDataModel
import com.spitzer.network.CountriesNetworkDatasource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FakeCountriesRemoteRepository @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkDatasource: CountriesNetworkDatasource
) : CountriesRepository {
    override val countriesData: Flow<List<CountryDataModel>> = flow {
        emit(
            networkDatasource.getCountriesInfo().mapNotNull {
                with(it) {
                    if (this.cca3.isNullOrEmpty() || this.name.isNullOrEmpty()) null
                    else CountryDataModel(
                        cca3 = this.cca3!!,
                        name = this.name!!,
                        capital = this.capital!!,
                        flags = CountryDataModel.Flags(
                            this.flags?.png,
                            this.flags?.svg
                        ),
                        area = this.area,
                        population = this.population
                    )
                }
            }
        )
    }.flowOn(ioDispatcher)

    override suspend fun updateCountries() {
        // nothing to do in this fake repository
    }

    override suspend fun upsertCountry(country: CountryDataModel) {
        withContext(ioDispatcher) {
            // TODO
        }
    }
}
