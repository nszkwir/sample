package com.spitzer.data.repository.languages

import com.spitzer.common.network.AppDispatchers
import com.spitzer.common.network.Dispatcher
import com.spitzer.data.repository.LanguagesRepository
import com.spitzer.model.data.ISOLanguage
import com.spitzer.network.com.spitzer.network.LanguagesNetworkDatasource
import com.spitzer.network.com.spitzer.network.model.asDataModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LanguagesRepositoryImpl @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val languagesNetworkDatasource: LanguagesNetworkDatasource
) : LanguagesRepository {

    private val _languages = MutableStateFlow<List<ISOLanguage>>(emptyList())
    override val languages: Flow<List<ISOLanguage>> get() = _languages

    init {
        CoroutineScope(SupervisorJob() + ioDispatcher).launch {
            //TODO implement DB and load from DB on init
            _languages.value = languagesNetworkDatasource.getLanguages().map {
                it.asDataModel()
            }
        }
    }

    override suspend fun getLanguages(): Map<String,ISOLanguage> {
        return _languages.value.associateBy({ it.code3 }, { it })
    }

    override suspend fun fetchLanguagesFromRemote() {
        withContext(ioDispatcher) {
            _languages.value = languagesNetworkDatasource.getLanguages().map {
                it.asDataModel()
            }
        }
    }

}
