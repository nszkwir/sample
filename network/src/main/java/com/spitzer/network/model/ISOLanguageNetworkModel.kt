package com.spitzer.network.com.spitzer.network.model

import com.spitzer.model.data.ISOLanguage
import kotlinx.serialization.Serializable

@Serializable
data class ISOLanguageNetworkModel(
    val language: String,
    val code2: String,
    val code3: String
)

fun ISOLanguageNetworkModel.asDataModel() =
    ISOLanguage(language, code2, code3)