package com.spitzer.network.model

import com.spitzer.model.data.CountryModel
import kotlinx.datetime.Clock
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


fun CountryInfoNetworkModel.asDataModel(): CountryModel? {
    return if (this.cca3.isEmpty() || this.name.common.isNullOrEmpty() || this.name.official.isNullOrEmpty()
        || this.nativeName.common.isNullOrEmpty() || this.nativeName.official.isNullOrEmpty()
    ) null
    else CountryModel(
        cca3 = this.cca3,
        name = CountryModel.Name(
            common = this.name.common,
            official = this.name.official
        ),
        nativeName = CountryModel.Name(
            common = this.nativeName.common,
            official = this.nativeName.official
        ),
        flag = CountryModel.Image(png = this.flag?.png, svg = this.flag?.svg),
        coatOfArms = CountryModel.Image(
            png = this.coatOfArms?.png,
            svg = this.coatOfArms?.svg
        ),
        currency = CountryModel.Currency(
            name = this.currency?.name,
            symbol = this.currency?.symbol
        ),
        capital = this.capital,
        area = this.area,
        population = this.population,
        timezones = this.timezones,
        maps = CountryModel.Maps(
            googleMaps = this.maps?.googleMaps,
            openStreetMaps = this.maps?.openStreetMaps
        ),
        publishDate = Clock.System.now()
    )
}
