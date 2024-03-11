package com.spitzer.database.testing

import com.spitzer.database.model.Car
import com.spitzer.database.model.Coordinates
import com.spitzer.database.model.Currency
import com.spitzer.database.model.Demonym
import com.spitzer.database.model.FakeRemoteCountryEntity
import com.spitzer.database.model.Idd
import com.spitzer.database.model.Maps
import com.spitzer.database.model.Name
import com.spitzer.database.model.NameValues
import com.spitzer.database.model.PostalCode
import com.spitzer.database.model.Symbol
import kotlinx.datetime.Clock

object TestFakeRemoteCountryEntityProvider {
    fun getFakeRemoteCountryEntity(
        cca3: String? = null,
        name: String? = null
    ) = FakeRemoteCountryEntity(
        name = Name(
            common = name ?: "Argentina",
            official = "República Argentina",
            nativeName = mapOf(
                Pair("eng", NameValues(official = "Argentine Republic", common = "Argentine")),
                Pair("esp", NameValues(official = "República Argentina", common = "Argentina"))
            ),
        ),
        cca2 = "AR",
        cca3 = cca3 ?: "ARG",
        tld = listOf(".ar",".arg",".gov.ar"),
        ccn3 = "ar",
        cioc = "ar",
        independent = true,
        status = "independent republic",
        isUNMember = true,
        currencies = mapOf(Pair("ARG", Currency("Peso argentino", "$"))),
        idd = Idd("+5", listOf("4")),
        capital = listOf("Buenos Aires"),
        altSpellings =listOf("AR", "Argentina"),
        region = "America",
        subregion = "South America",
        languages = listOf("spa", "que"),
        landlocked = false,
        borders = listOf("CHL", "BRA", "URU", "BOL", "PAR"),
        area = 7371238.1,
        demonyms = mapOf(
            Pair(
                "esp",
                Demonym(f = "argentina", m = "argentino")
            )
        ),
        flag = "",
        maps = Maps(null, null),
        population = 48000000,
        gini = mapOf(Pair("2023", 123.3)),
        fifa = "ARG",
        car = Car(listOf("ARG","AR"), "left"),
        timezones = listOf("TIMEZONE +3"),
        continents = listOf("Americas"),
        flags = Symbol(null, null, null),
        coatOfArms = Symbol(null, null, null),
        startOfWeek = "Monday",
        postalCode = PostalCode(null, null),
        tags = "Argentina Republica Argentina Buenos Aires Tango Messi Maradona",
        coordinates = Coordinates(0.0, 0.0),
        capitalCoordinates = Coordinates(0.0, 0.0),
        publishDate = Clock.System.now(),
        updateDate = null,
        countryNameTranslations = mapOf<String,NameValues>(
            Pair("eng",NameValues(official = "Argentine Republic", common = "Argentine")),
            Pair("fra",NameValues(official = "Le Republique Argentine", common = "Argentine")),
            Pair("esp",NameValues(official = "Republica Argentina", common = "Argentina")),
        )
    )
}
