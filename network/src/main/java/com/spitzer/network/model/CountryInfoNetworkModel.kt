package com.spitzer.network.model

import kotlinx.serialization.Serializable

@Serializable
data class CountryInfoNetworkModel(
    val cca3: String,
    val name: Name,
    val nativeName: Name,
    val flag: Image?,
    val coatOfArms: Image?,
    val capital: String,
    val area: Double?,
    val population: Long?,
    val currency: Currency?,
    val maps: Maps?,
    val timezones: List<String>?
) {
    @Serializable
    data class Image(
        val png: String?,
        val svg: String?
    )

    @Serializable
    data class Name(
        val official: String?,
        val common: String?
    )

    @Serializable
    data class Currency(
        val name: String?,
        val symbol: String?
    )

    @Serializable
    data class Maps(
        val googleMaps: String?,
        val openStreetMaps: String?
    )
}
