package com.spitzer.network.com.spitzer.network

import com.spitzer.network.com.spitzer.network.model.ISOLanguageNetworkModel

interface LanguagesNetworkDatasource {
    suspend fun getLanguages(): List<ISOLanguageNetworkModel>
}
