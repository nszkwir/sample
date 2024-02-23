package com.spitzer.model.data

import kotlinx.datetime.Instant


data class CountryModel(
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
    val timezones: List<String>?,
    val publishDate: Instant
) {
    data class Image(
        val png: String?,
        val svg: String?
    )

    data class Name(
        val official: String,
        val common: String
    )

    data class Currency(
        val name: String?,
        val symbol: String?
    )

    data class Maps(
        val googleMaps: String?,
        val openStreetMaps: String?
    )
}
