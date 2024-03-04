package com.spitzer.data.repository

import com.spitzer.model.data.ISOLanguage

interface FakeLanguagesRemoteRepository {
    var languages: List<ISOLanguage>
}
