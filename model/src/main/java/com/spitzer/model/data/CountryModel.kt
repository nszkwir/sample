package com.spitzer.model.data

import kotlinx.datetime.Instant

data class CountryModel(
    val name: Name,
    val tags: String,
    val tld: List<String>,
    val cca2: String,
    val ccn3: String?,
    val cca3: String,
    val cioc: String?,
    val independent: Boolean?, // if null, then it's unknown
    val status: String,
    val isUNMember: Boolean,
    val currencies: Map<String, Currency>, // currency code to currency
    val idd: Idd, // Related to phone codes
    val capital: List<String>,
    val altSpellings: List<String>,
    val region: String,
    val subregion: String?,
    val languages: List<String>, // languages spoken
    val countryNameTranslations: Map<String, NameValues>,// language to translation
    val coordinates: Coordinates?,
    val landlocked: Boolean,
    val borders: List<String>, // a list of the cca3 codes of those countries with shared borders
    val area: Double,
    val demonyms: Map<String, Demonym>, // lang to demonym
    val flag: String, // the emoji code of the flag
    val maps: Maps,
    val population: Long,
    val gini: Map<String, Double>, // year to value
    val fifa: String?, // is a three letters code designed by the Fifa. If null, then unknown or undetermined
    val car: Car,
    val timezones: List<String>,
    val continents: List<String>,
    val flags: Symbol,
    val coatOfArms: Symbol,
    val startOfWeek: String,
    val capitalCoordinates: Coordinates?,
    val postalCode: PostalCode?,
    val publishDate: Instant,
    val updateDate: Instant?
) {

    data class Name(
        val common: String,
        val official: String,
        val nativeName: Map<String, NameValues>
    )

    data class NameValues(
        val official: String?,
        val common: String?
    )

    /** Currencies are mapped to currency codes */
    data class Currency(
        val name: String?, // example: Euro
        val symbol: String? // example: $
    )

    /** Idd is referred to country telephone codes*/
    data class Idd(
        val root: String, // example: +31
        val suffixes: List<String> // example: 92, 99
    )

    /** How males and females are called */
    data class Demonym(
        val f: String, // example: Española
        val m: String // example: Español
    )

    data class Maps(
        val googleMapsLink: String?,
        val openStreetMapsLink: String?
    )

    data class Car(
        val signs: List<String>, // the car plates signs
        val drivingSide: DrivingSide
    ) {
        enum class DrivingSide { LEFT, RIGHT, UNDETERMINED }
    }

    data class Symbol(
        val png: String?,
        val svg: String?,
        val alt: String?
    )

    data class PostalCode(
        val format: String?,
        val regex: String?
    )
}
