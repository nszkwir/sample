package com.spitzer.data.repository

import com.spitzer.model.data.ISOLanguage
import kotlinx.coroutines.flow.Flow

interface LanguagesRepository {
    val languages: Flow<List<ISOLanguage>>
    suspend fun getLanguages(): Map<String,ISOLanguage>
    suspend fun fetchLanguagesFromRemote()
}
