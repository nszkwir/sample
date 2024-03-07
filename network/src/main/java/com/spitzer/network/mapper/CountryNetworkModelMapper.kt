package com.spitzer.network.com.spitzer.network.mapper

import com.spitzer.model.data.Coordinates
import com.spitzer.model.data.CountryModel
import com.spitzer.network.model.CountryNetworkModel
import kotlinx.datetime.Clock
import javax.inject.Inject

class CountryNetworkModelMapper @Inject constructor(

) : NetworkMapper<CountryNetworkModel, CountryModel> {
    override fun mapToModel(networkModel: CountryNetworkModel): CountryModel? {

        return with(networkModel) {
            if (this.name.common.isNullOrEmpty() || this.name.official.isNullOrEmpty()) null
            else CountryModel(
                name = CountryModel.Name(
                    common = this.name.common,
                    official = this.name.official,
                    nativeName = mapNativeNames(this.name.nativeName)
                ),
                tags = getTags(this),
                tld = this.tld ?: emptyList(),
                cca2 = this.cca2,
                ccn3 = this.ccn3,
                cca3 = this.cca3,
                cioc = this.cioc,
                independent = this.independent,
                status = this.status,
                isUNMember = this.isUNMember,
                currencies = mapCurrencies(this.currencies),
                idd = mapIdd(this.idd),
                capital = this.capital ?: emptyList(),
                altSpellings = this.altSpellings ?: emptyList(),
                region = this.region,
                subregion = this.subregion,
                languages = this.languages?.mapNotNull { it.key } ?: emptyList(),
                countryNameTranslations = mapTranslations(this.translations),
                coordinates = mapCoordinates(this.latlng),
                landlocked = this.landlocked,
                borders = this.borders ?: emptyList(),
                area = this.area,
                demonyms = mapDemonyms(this.demonyms),
                flag = this.flag,
                maps = mapMaps(this.maps),
                population = this.population,
                gini = this.gini ?: emptyMap(),
                fifa = this.fifa,
                car = mapCar(this.car),
                timezones = this.timezones,
                continents = this.continents,
                flags = mapFlags(this.flags),
                coatOfArms = mapCoatOfArms(this.coatOfArms),
                startOfWeek = this.startOfWeek,
                capitalCoordinates = mapCoordinates(this.capitalInfo.latlng),
                postalCode = mapPostalCode(this.postalCode),
                publishDate = Clock.System.now(),
                updateDate = null
            )
        }
    }

    private fun getTags(country: CountryNetworkModel): String {
        return ("${country.name.common} ${country.name.official} ${country.cca3} " +
                "${
                    country.name.nativeName?.map {
                        "${it.value.common} ${it.value.official}"
                    }?.joinToString(" ")
                } " + "${country.capital?.joinToString(" ")} " +
                "${country.altSpellings?.joinToString(" ")}").lowercase()
    }

    private fun mapNativeNames(networkData: Map<String, CountryNetworkModel.NativeLanguage>?) =
        networkData?.mapValues { nativeLanguage ->
            CountryModel.NameValues(
                official = nativeLanguage.value.official,
                common = nativeLanguage.value.common
            )
        } ?: emptyMap()

    private fun mapCurrencies(networkData: Map<String, CountryNetworkModel.Currency>?) =
        networkData?.mapValues { currency ->
            CountryModel.Currency(name = currency.value.name, symbol = currency.value.symbol)
        } ?: emptyMap()

    private fun mapIdd(networkData: CountryNetworkModel.Idd) =
        CountryModel.Idd(
            root = networkData.root ?: "",
            suffixes = networkData.suffixes ?: emptyList()
        )

    private fun mapTranslations(networkData: Map<String, CountryNetworkModel.Translation>?) =
        networkData?.mapValues { translation ->
            CountryModel.NameValues(
                official = translation.value.official,
                common = translation.value.common
            )
        } ?: emptyMap()

    private fun mapCoordinates(networkData: List<Double>?): Coordinates? {
        val lat = networkData?.getOrNull(0)
        val lon = networkData?.getOrNull(1)
        return if (lat == null || lon == null) null
        else Coordinates(latitude = lat, longitude = lon)
    }

    private fun mapMaps(networkData: CountryNetworkModel.Maps?) =
        CountryModel.Maps(
            googleMapsLink = networkData?.googleMapsLink,
            openStreetMapsLink = networkData?.openStreetMapsLink
        )

    private fun mapCar(networkData: CountryNetworkModel.Car?) =
        CountryModel.Car(
            signs = networkData?.signs ?: emptyList(),
            drivingSide = when (networkData?.side) {
                "left" -> CountryModel.Car.DrivingSide.LEFT
                "right" -> CountryModel.Car.DrivingSide.RIGHT
                else -> CountryModel.Car.DrivingSide.UNDETERMINED
            }
        )

    private fun mapDemonyms(networkData: Map<String, CountryNetworkModel.Demonym>?) =
        networkData?.mapValues { demonym ->
            CountryModel.Demonym(f = demonym.value.f, m = demonym.value.m)
        } ?: emptyMap()

    private fun mapPostalCode(networkData: CountryNetworkModel.PostalCode?) =
        CountryModel.PostalCode(format = networkData?.format, regex = networkData?.regex)

    private fun mapFlags(networkData: CountryNetworkModel.Flags?) =
        CountryModel.Symbol(png = networkData?.png, svg = networkData?.svg, alt = networkData?.alt)

    private fun mapCoatOfArms(networkData: CountryNetworkModel.CoatOfArms?) =
        CountryModel.Symbol(png = networkData?.png, svg = networkData?.svg, alt = null)
}
