package com.spitzer.network.com.spitzer.network.fake

import com.spitzer.common.network.AppDispatchers
import com.spitzer.common.network.Dispatcher
import com.spitzer.network.JvmUnitTestFakeAssetManager
import com.spitzer.network.com.spitzer.network.LanguagesNetworkDatasource
import com.spitzer.network.com.spitzer.network.model.ISOLanguageNetworkModel
import com.spitzer.network.fake.FakeAssetManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject

class FakeLanguagesNetworkDatasource @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json,
    private val assets: FakeAssetManager = JvmUnitTestFakeAssetManager,
) : LanguagesNetworkDatasource {

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getLanguages(): List<ISOLanguageNetworkModel> =
        withContext(ioDispatcher) {
            assets.open(LANGUAGES_ASSET).use(networkJson::decodeFromStream)
        }

    companion object {
        private const val LANGUAGES_ASSET = "languages.json"
    }
}
