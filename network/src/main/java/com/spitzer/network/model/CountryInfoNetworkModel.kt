package com.spitzer.network.model

import kotlinx.serialization.Serializable

@Serializable
data class CountryInfoNetworkModel(
    val cca3: String?,
    val name: String?,
    val flags: Flags?,
    val capital: String?,
    val area: Double = 0.0,
    val population: Long = 0,
) {
    @Serializable
    data class Flags(
        val png: String?,
        val svg: String?
    )
}
