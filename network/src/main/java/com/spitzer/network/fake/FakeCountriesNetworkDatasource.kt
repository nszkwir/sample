package com.spitzer.network.fake

import com.spitzer.common.network.AppDispatchers
import com.spitzer.common.network.Dispatcher
import com.spitzer.network.CountriesNetworkDatasource
import com.spitzer.network.JvmUnitTestFakeAssetManager
import com.spitzer.network.model.CountryInfoNetworkModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject

class FakeCountriesNetworkDatasource @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json,
    private val assets: FakeAssetManager = JvmUnitTestFakeAssetManager,
) : CountriesNetworkDatasource {

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getCountriesInfo(): List<CountryInfoNetworkModel> =
        withContext(ioDispatcher) {
            assets.open(COUNTRIES_ASSET).use(networkJson::decodeFromStream)
        }

    companion object {
        private const val COUNTRIES_ASSET = "countries.json"
    }
}
