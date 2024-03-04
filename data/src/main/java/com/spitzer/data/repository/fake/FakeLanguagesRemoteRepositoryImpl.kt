package com.spitzer.data.repository.fake

import com.spitzer.common.network.AppDispatchers
import com.spitzer.common.network.Dispatcher
import com.spitzer.data.repository.FakeLanguagesRemoteRepository
import com.spitzer.model.data.ISOLanguage
import com.spitzer.network.com.spitzer.network.LanguagesNetworkDatasource
import com.spitzer.network.com.spitzer.network.model.asDataModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class FakeLanguagesRemoteRepositoryImpl @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val languagesNetworkDatasource: LanguagesNetworkDatasource
) : FakeLanguagesRemoteRepository {

    override lateinit var languages: List<ISOLanguage>

    init {
        CoroutineScope(SupervisorJob() + ioDispatcher).launch {
            languages = languagesNetworkDatasource.getLanguages().map { it.asDataModel() }
        }
    }
}
