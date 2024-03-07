package com.spitzer.ui.tooling

import com.spitzer.model.data.Coordinates
import com.spitzer.model.data.CountryModel
import kotlinx.datetime.Clock

object TestCountryModelProvider {
    fun getTestCountryModel(): CountryModel {
        return CountryModel(
            name = CountryModel.Name(
                common = "Argentina",
                official = "República Argentina",
                nativeName = mapOf(
                    Pair(
                        "eng", CountryModel.NameValues(
                            official = "Argentine Republic",
                            common = "Argentine"
                        )
                    )
                ),
            ),
            cca2 = "AR",
            cca3 = "ARG",
            tags = "Argentina Buenos Aires ARG ARG",
            tld = emptyList(),
            ccn3 = null,
            cioc = null,
            independent = true,
            status = "",
            isUNMember = true,
            currencies = mapOf(Pair("ARG", CountryModel.Currency("Peso argentino", "$"))),
            idd = CountryModel.Idd("+54", emptyList()),
            capital = listOf("Buenos Aires"),
            altSpellings = emptyList(),
            region = "America",
            subregion = "South America",
            languages = listOf("Spanish, Quechua"),
            countryNameTranslations = emptyMap(),
            coordinates = Coordinates(0.0, 0.0),
            landlocked = false,
            borders = listOf("CHL", "BRA", "URU", "BOL", "PAR"),
            area = 7371238.1,
            demonyms = mapOf(Pair("esp", CountryModel.Demonym(f = "argentina", m = "argentino"))),
            flag = "",
            maps = CountryModel.Maps(null, null),
            population = 48000000,
            gini = mapOf(Pair("2023", 123.3)),
            fifa = "ARG",
            car = CountryModel.Car(emptyList(), CountryModel.Car.DrivingSide.LEFT),
            timezones = emptyList(),
            continents = emptyList(),
            flags = CountryModel.Symbol(null, null, null),
            coatOfArms = CountryModel.Symbol(null, null, null),
            startOfWeek = "Monday",
            capitalCoordinates = Coordinates(0.0, 0.0),
            postalCode = CountryModel.PostalCode(null, null),
            publishDate = Clock.System.now(),
            updateDate = null
        )
    }
}
