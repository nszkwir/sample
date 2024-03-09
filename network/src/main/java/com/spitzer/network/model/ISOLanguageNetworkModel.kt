package com.spitzer.network.com.spitzer.network.model

import com.spitzer.model.data.ISOLanguage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ISOLanguageNetworkModel(
    @SerialName("language")
    val language: String,
    @SerialName("2code")
    val code2: String,
    @SerialName("3code")
    val code3: String
)

fun ISOLanguageNetworkModel.asDataModel() =
    ISOLanguage(language, code2, code3)