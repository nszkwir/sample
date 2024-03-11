package com.spitzer.network.com.spitzer.network.testing

import com.spitzer.network.com.spitzer.network.model.ISOLanguageNetworkModel

object TestISOLanguageNetworkModelProvider {

    fun getNetworkModel(
        language: String? = null,
        code2: String? = null,
        code3: String? = null
    ): ISOLanguageNetworkModel {
        return ISOLanguageNetworkModel(
            language = language ?: "Spanish",
            code2 = code2 ?: "sp",
            code3 = code3 ?: "spa"
        )
    }
}
