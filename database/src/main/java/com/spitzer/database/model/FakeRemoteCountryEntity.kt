package com.spitzer.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.spitzer.model.data.CountryModel
import kotlinx.datetime.Instant

@Entity(
    tableName = "fake_remote_countries",
)
data class FakeRemoteCountryEntity(
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

fun FakeRemoteCountryEntity.asDataModel() = CountryModel(
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

fun FakeRemoteCountryEntity.asCountryEntity() = CountryEntity(
    name = this.name,
    tags = this.tags,
    tld = this.tld,
    cca2 = this.cca2,
    ccn3 = this.ccn3,
    cca3 = this.cca3,
    cioc = this.cioc,
    independent = this.independent,
    status = this.status,
    isUNMember = this.isUNMember,
    currencies = this.currencies,
    idd = this.idd,
    capital = this.capital,
    altSpellings = this.altSpellings,
    region = this.region,
    subregion = this.subregion,
    languages = this.languages,
    countryNameTranslations = this.countryNameTranslations,
    coordinates = this.coordinates,
    landlocked = this.landlocked,
    borders = this.borders,
    area = this.area,
    demonyms = this.demonyms,
    flag = this.flag,
    maps = this.maps,
    population = this.population,
    gini = this.gini,
    fifa = this.fifa,
    car = this.car,
    timezones = this.timezones,
    continents = this.continents,
    flags = this.flags,
    coatOfArms = this.coatOfArms,
    startOfWeek = this.startOfWeek,
    capitalCoordinates = this.capitalCoordinates,
    postalCode = this.postalCode,
    publishDate = this.publishDate,
    updateDate = this.updateDate
)
