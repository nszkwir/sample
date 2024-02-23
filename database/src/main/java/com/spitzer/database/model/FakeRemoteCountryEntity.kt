package com.spitzer.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.spitzer.model.data.CountryModel
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

@Entity(
    tableName = "fake_remote_countries",
)
data class FakeRemoteCountryEntity(
    @PrimaryKey
    val cca3: String,
    @ColumnInfo(name = "official_name")
    val officialName: String,
    @ColumnInfo(name = "common_name")
    val commonName: String,
    @ColumnInfo(name = "native_official_name")
    val nativeOfficialName: String,
    @ColumnInfo(name = "native_common_name")
    val nativeCommonName: String,
    @ColumnInfo(name = "flag_png_url")
    val flagPngUrl: String?,
    @ColumnInfo(name = "flag_svg_url")
    val flagSvgUrl: String?,
    @ColumnInfo(name = "coat_of_arms_png_url")
    val coatOfArmsPngUrl: String?,
    @ColumnInfo(name = "coat_of_arms_svg_url")
    val coatOfArmsSvgUrl: String?,
    @ColumnInfo(name = "currency_name")
    val currencyName: String?,
    @ColumnInfo(name = "currency_symbol")
    val currencySymbol: String?,
    val timezones: List<String>?,
    @ColumnInfo(name = "google_maps")
    val googleMaps: String?,
    @ColumnInfo(name = "openstreet_maps")
    val openStreetMaps: String?,
    val capital: String,
    val area: Double?,
    val population: Long?,
    @ColumnInfo(name = "publish_date")
    val publishDate: Instant,
    @ColumnInfo(name = "update_date")
    val updateDate: Instant
)

fun FakeRemoteCountryEntity.asDataModel() = CountryModel(
    cca3 = this.cca3,
    name = CountryModel.Name(common = this.commonName, official = this.officialName),
    nativeName = CountryModel.Name(
        common = this.nativeCommonName,
        official = this.nativeOfficialName
    ),
    flag = CountryModel.Image(png = this.flagPngUrl, svg = this.flagSvgUrl),
    coatOfArms = CountryModel.Image(png = this.coatOfArmsPngUrl, svg = this.coatOfArmsSvgUrl),
    currency = CountryModel.Currency(name = this.currencyName, symbol = this.currencySymbol),
    capital = this.capital,
    area = this.area,
    population = this.population,
    timezones = this.timezones,
    maps = CountryModel.Maps(googleMaps = this.googleMaps, openStreetMaps = this.openStreetMaps),
    publishDate = this.publishDate
)


fun FakeRemoteCountryEntity.asCountryEntity() = CountryEntity(
    cca3 = this.cca3,
    commonName = commonName,
    officialName = officialName,
    nativeCommonName = nativeCommonName,
    nativeOfficialName = nativeOfficialName,
    flagPngUrl = flagPngUrl,
    flagSvgUrl = flagSvgUrl,
    coatOfArmsPngUrl = coatOfArmsPngUrl,
    coatOfArmsSvgUrl = coatOfArmsSvgUrl,
    currencyName = currencyName,
    currencySymbol = currencySymbol,
    capital = this.capital,
    area = this.area,
    population = this.population,
    timezones = this.timezones,
    googleMaps = googleMaps,
    openStreetMaps = openStreetMaps,
    publishDate = publishDate,
    updateDate = Clock.System.now()
)
