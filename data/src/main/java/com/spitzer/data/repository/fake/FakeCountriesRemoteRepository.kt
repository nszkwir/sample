package com.spitzer.data.repository.fake

import com.spitzer.common.network.AppDispatchers
import com.spitzer.common.network.Dispatcher
import com.spitzer.data.repository.CountriesRepository
import com.spitzer.model.data.CountryModel
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
    override val countriesData: Flow<List<CountryModel>> = flow {
        emit(
            networkDatasource.getCountriesInfo().mapNotNull {
                with(it) {
                    if (this.cca3.isEmpty() || this.name.common.isNullOrEmpty()) null
                    else CountryModel(
                        cca3 = this.cca3,
                        name = CountryModel.Name(
                            common = this.name.common,
                            official = this.name.official
                        ),
                        nativeName = CountryModel.Name(
                            common = this.nativeName.common,
                            official = this.nativeName.official
                        ),
                        flag = CountryModel.Image(png = this.flag?.png, svg = this.flag?.svg),
                        coatOfArms = CountryModel.Image(
                            png = this.coatOfArms?.png,
                            svg = this.coatOfArms?.svg
                        ),
                        currency = CountryModel.Currency(
                            name = this.currency?.name,
                            symbol = this.currency?.symbol
                        ),
                        capital = this.capital,
                        area = this.area,
                        population = this.population,
                        timezones = this.timezones,
                        maps = CountryModel.Maps(
                            googleMaps = this.maps?.googleMaps,
                            openStreetMaps = this.maps?.openStreetMaps
                        )
                    )
                }
            }
        )
    }.flowOn(ioDispatcher)

    override suspend fun updateCountries() {
        // nothing to do in this fake repository
    }

    override suspend fun upsertCountry(country: CountryModel) {
        withContext(ioDispatcher) {
            // TODO
        }
    }
}
