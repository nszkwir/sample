package com.spitzer.data.fake

import com.spitzer.common.network.AppDispatchers
import com.spitzer.common.network.Dispatcher
import com.spitzer.data.repository.LanguagesRepository
import com.spitzer.model.data.ISOLanguage
import com.spitzer.model.testing.TestISOLanguageProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class FakeLanguagesRepository @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : LanguagesRepository {

    private val _languages = MutableStateFlow<List<ISOLanguage>>(emptyList())
    override val languages: Flow<List<ISOLanguage>> get() = _languages

    init {
        _languages.update {
            TestISOLanguageProvider.getISOLanguageList()
        }
    }

    override suspend fun getLanguages(): Map<String, ISOLanguage> {
        return _languages.value.associateBy({ it.code3 }, { it })
    }

    override suspend fun fetchLanguagesFromRemote() {
        // do nothing
    }
}
