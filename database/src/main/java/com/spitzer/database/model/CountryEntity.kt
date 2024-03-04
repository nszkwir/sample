package com.spitzer.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.spitzer.model.data.CountryModel
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Entity(
    tableName = "countries",
)
data class CountryEntity(
    val name: Name,
    val tags: String,
    val tld: List<String>,
    val cca2: String,
    val ccn3: String?,
    @PrimaryKey
    val cca3: String,
    val cioc: String?,
    val independent: Boolean?,
    val status: String,
    val isUNMember: Boolean,
    val currencies: Map<String, Currency>,
    val idd: Idd,
    val capital: List<String>,
    val altSpellings: List<String>,
    val region: String,
    val subregion: String?,
    val languages: List<String>,
    val countryNameTranslations: Map<String, NameValues>,
    val coordinates: Coordinates?,
    val landlocked: Boolean,
    val borders: List<String>,
    val area: Double,
    val demonyms: Map<String, Demonym>,
    val flag: String,
    val maps: Maps,
    val population: Long,
    val gini: Map<String, Double>,
    val fifa: String?,
    val car: Car,
    val timezones: List<String>,
    val continents: List<String>,
    val flags: Symbol,
    val coatOfArms: Symbol,
    val startOfWeek: String,
    val capitalCoordinates: Coordinates?,
    val postalCode: PostalCode?,
    @ColumnInfo(name = "publish_date")
    val publishDate: Instant,
    @ColumnInfo(name = "update_date")
    val updateDate: Instant?
)

@Serializable
data class Name(val common: String, val official: String, val nativeName: Map<String, NameValues>)
@Serializable
data class NameValues(val official: String?, val common: String?)
@Serializable
data class Currency(val name: String?, val symbol: String?)
@Serializable
data class Idd(val root: String, val suffixes: List<String>)
@Serializable
data class Demonym(val f: String, val m: String)
@Serializable
data class Maps(val googleMapsLink: String?, val openStreetMapsLink: String?)
@Serializable
data class Car(val signs: List<String>, val drivingSide: String?)
@Serializable
data class Symbol(val png: String?, val svg: String?, val alt: String?)
@Serializable
data class PostalCode(val format: String?, val regex: String?)
@Serializable
data class Coordinates(val latitude: Double, val longitude: Double)

fun CountryEntity.asDataModel() = CountryModel(
    name = CountryModel.Name(
        common = this.name.common,
        official = this.name.official,
        nativeName = this.name.nativeName.mapValues {
            CountryModel.NameValues(
                official = it.value.official,
                common = it.value.common
            )
        }
    ),
    tags = this.tags,
    tld = this.tld,
    cca2 = this.cca2,
    ccn3 = this.ccn3,
    cca3 = this.cca3,
    cioc = this.cioc,
    independent = this.independent,
    status = this.status,
    isUNMember = this.isUNMember,
    currencies = this.currencies.mapValues {
        CountryModel.Currency(
            name = it.value.name,
            symbol = it.value.symbol
        )
    },
    idd = CountryModel.Idd(root = this.idd.root, suffixes = this.idd.suffixes),
    capital = this.capital,
    altSpellings = this.altSpellings,
    region = this.region,
    subregion = this.subregion,
    languages = this.languages,
    countryNameTranslations = this.countryNameTranslations.mapValues {
        CountryModel.NameValues(
            official = it.value.official,
            common = it.value.common
        )
    },
    coordinates = this.coordinates.asDataModel(),
    landlocked = this.landlocked,
    borders = this.borders,
    area = this.area,
    demonyms = this.demonyms.mapValues {
        CountryModel.Demonym(
            f = it.value.f,
            m = it.value.m
        )
    },
    flag = this.flag,
    maps = CountryModel.Maps(
        googleMapsLink = this.maps.googleMapsLink,
        openStreetMapsLink = this.maps.openStreetMapsLink
    ),
    population = this.population,
    gini = this.gini,
    fifa = this.fifa,
    car = CountryModel.Car(
        signs = this.car.signs,
        drivingSide = when (this.car.drivingSide) {
            "left" -> CountryModel.Car.DrivingSide.LEFT
            "right" -> CountryModel.Car.DrivingSide.RIGHT
            else -> CountryModel.Car.DrivingSide.UNDETERMINED
        }
    ),
    timezones = this.timezones,
    continents = this.continents,
    flags = CountryModel.Symbol(svg = this.flags.svg, png = this.flags.png, alt = this.flags.alt),
    coatOfArms = CountryModel.Symbol(
        svg = this.coatOfArms.svg,
        png = this.coatOfArms.png,
        alt = this.coatOfArms.alt
    ),
    startOfWeek = this.startOfWeek,
    capitalCoordinates = this.capitalCoordinates?.asDataModel(),
    postalCode = CountryModel.PostalCode(
        format = this.postalCode?.format,
        regex = this.postalCode?.regex
    ),
    publishDate = this.publishDate,
    updateDate = this.updateDate
)

fun Coordinates?.asDataModel(): com.spitzer.model.data.Coordinates? {
    return if (this == null) null
    else com.spitzer.model.data.Coordinates(latitude = this.latitude, longitude = this.longitude)
}
