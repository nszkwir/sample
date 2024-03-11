package com.spitzer.model.testing

import com.spitzer.model.data.Coordinates
import com.spitzer.model.data.CountryModel
import kotlinx.datetime.Clock

object TestCountryModelProvider {
    fun getTestCountryModel(cca3: String? = null, name: String? = null) = CountryModel(
        name = CountryModel.Name(
            common = name ?: "Argentina",
            official = "República Argentina",
            nativeName = mapOf(
                Pair(
                    "eng",
                    CountryModel.NameValues(official = "Argentine Republic", common = "Argentine")
                ),
                Pair(
                    "esp",
                    CountryModel.NameValues(official = "República Argentina", common = "Argentina")
                )
            ),
        ),
        cca2 = "AR",
        cca3 = cca3 ?: "ARG",
        tld = listOf(".ar", ".arg", ".gov.ar"),
        ccn3 = "ar",
        cioc = "ar",
        independent = true,
        status = "independent republic",
        isUNMember = true,
        currencies = mapOf(Pair("ARG", CountryModel.Currency("Peso argentino", "$"))),
        idd = CountryModel.Idd("+5", listOf("4")),
        capital = listOf("Buenos Aires"),
        altSpellings = listOf("AR", "Argentina"),
        region = "America",
        subregion = "South America",
        languages = listOf("spa", "que"),
        landlocked = false,
        borders = listOf("CHL", "BRA", "URU", "BOL", "PAR"),
        area = 7371238.1,
        demonyms = mapOf(
            Pair(
                "esp",
                CountryModel.Demonym(f = "argentina", m = "argentino")
            )
        ),
        flag = "",
        maps = CountryModel.Maps(null, null),
        population = 48000000,
        gini = mapOf(Pair("2023", 123.3)),
        fifa = "ARG",
        car = CountryModel.Car(listOf("ARG", "AR"), CountryModel.Car.DrivingSide.LEFT),
        timezones = listOf("TIMEZONE +3"),
        continents = listOf("Americas"),
        flags = CountryModel.Symbol(null, null, null),
        coatOfArms = CountryModel.Symbol(null, null, null),
        startOfWeek = "Monday",
        postalCode = CountryModel.PostalCode(null, null),
        tags = "Argentina Republica Argentina Buenos Aires Tango Messi Maradona",
        coordinates = Coordinates(0.0, 0.0),
        capitalCoordinates = Coordinates(0.0, 0.0),
        publishDate = Clock.System.now(),
        updateDate = null,
        countryNameTranslations = mapOf<String, CountryModel.NameValues>(
            Pair(
                "eng",
                CountryModel.NameValues(official = "Argentine Republic", common = "Argentine")
            ),
            Pair(
                "fra",
                CountryModel.NameValues(official = "Le Republique Argentine", common = "Argentine")
            ),
            Pair(
                "esp",
                CountryModel.NameValues(official = "Republica Argentina", common = "Argentina")
            ),
        )
    )
}
