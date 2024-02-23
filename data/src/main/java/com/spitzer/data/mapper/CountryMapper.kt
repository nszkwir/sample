package com.spitzer.data.mapper

import com.spitzer.database.model.CountryEntity
import com.spitzer.database.model.FakeRemoteCountryEntity
import com.spitzer.model.data.CountryModel
import kotlinx.datetime.Clock

fun CountryModel.asCountryEntity(): CountryEntity {
    return CountryEntity(
        cca3 = this.cca3,
        commonName = this.name.common,
        officialName = this.name.official,
        nativeCommonName = this.nativeName.common,
        nativeOfficialName = this.nativeName.official,
        flagPngUrl = this.flag?.png,
        flagSvgUrl = this.flag?.svg,
        coatOfArmsPngUrl = this.coatOfArms?.png,
        coatOfArmsSvgUrl = this.coatOfArms?.svg,
        currencyName = this.currency?.name,
        currencySymbol = this.currency?.symbol,
        capital = this.capital,
        area = this.area,
        population = this.population,
        timezones = this.timezones,
        googleMaps = this.maps?.googleMaps,
        openStreetMaps = this.maps?.openStreetMaps,
        publishDate = this.publishDate,
        updateDate = Clock.System.now()
    )
}

fun CountryModel.asFakeRemoteCountryEntity(): FakeRemoteCountryEntity {
    return FakeRemoteCountryEntity(
        cca3 = this.cca3,
        commonName = this.name.common,
        officialName = this.name.official,
        nativeCommonName = this.nativeName.common,
        nativeOfficialName = this.nativeName.official,
        flagPngUrl = this.flag?.png,
        flagSvgUrl = this.flag?.svg,
        coatOfArmsPngUrl = this.coatOfArms?.png,
        coatOfArmsSvgUrl = this.coatOfArms?.svg,
        currencyName = this.currency?.name,
        currencySymbol = this.currency?.symbol,
        capital = this.capital,
        area = this.area,
        population = this.population,
        timezones = this.timezones,
        googleMaps = this.maps?.googleMaps,
        openStreetMaps = this.maps?.openStreetMaps,
        publishDate = this.publishDate,
        updateDate = Clock.System.now()
    )
}