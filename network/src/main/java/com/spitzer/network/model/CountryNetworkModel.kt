package com.spitzer.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryNetworkModel(
    val name: Name,
    val tld: List<String>? = emptyList(),
    val cca2: String,
    val ccn3: String? = null,
    val cca3: String,
    val cioc: String? = null,
    val independent: Boolean? = null,
    val status: String,
    @SerialName("unMember")
    val isUNMember: Boolean,
    val currencies: Map<String, Currency>? = null, // currency code to currency
    val idd: Idd,
    val capital: List<String>? = null,
    val altSpellings: List<String>? = null,
    val region: String,
    val subregion: String? = null,
    val languages: Map<String, String>? = null, // lang to language
    val translations: Map<String, Translation>? = null,// lang to translation
    val latlng: List<Double>? = null,
    val landlocked: Boolean,
    val borders: List<String>? = null,
    val area: Double,
    val demonyms: Map<String, Demonym>? = null, // lang to demonym
    val flag: String,
    val maps: Maps,
    val population: Long,
    val gini: Map<String, Double>? = null, // year to value
    val fifa: String? = null,
    val car: Car,
    val timezones: List<String>,
    val continents: List<String>,
    val flags: Flags,
    val coatOfArms: CoatOfArms,
    val startOfWeek: String,
    val capitalInfo: CapitalInfo,
    val postalCode: PostalCode? = null
) {

    @Serializable
    data class Name(
        val common: String? = null,
        val official: String? = null,
        val nativeName: Map<String, NativeLanguage>? = null // maps lang code of 3 length to NativeLanguage
    )

    @Serializable
    data class NativeLanguage(
        val official: String? = null,
        val common: String? = null
    )

    @Serializable
    data class Currency(
        val name: String? = null,
        val symbol: String? = null
    )

    @Serializable
    data class Idd(
        val root: String? = null,
        val suffixes: List<String>? = null
    )

    @Serializable
    data class Translation(
        val official: String? = null,
        val common: String? = null
    )

    @Serializable
    data class Maps(
        @SerialName("googleMaps")
        val googleMapsLink: String? = null,
        @SerialName("openStreetMaps")
        val openStreetMapsLink: String? = null
    )

    @Serializable
    data class Demonym(
        val f: String,
        val m: String
    )

    @Serializable
    data class Flags(
        val png: String? = null,
        val svg: String? = null,
        val alt: String? = null
    )

    @Serializable
    data class CoatOfArms(
        val png: String? = null,
        val svg: String? = null
    )

    @Serializable
    data class Car(
        val signs: List<String>? = null,
        val side: String? = null
    )

    @Serializable
    data class CapitalInfo(
        val latlng: List<Double>? = null
    )

    @Serializable
    data class PostalCode(
        val format: String? = null,
        val regex: String? = null
    )
}
