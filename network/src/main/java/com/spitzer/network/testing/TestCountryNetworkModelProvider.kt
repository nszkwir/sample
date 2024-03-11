package com.spitzer.network.com.spitzer.network.testing

import com.spitzer.network.model.CountryNetworkModel

object TestCountryNetworkModelProvider {
    fun getCountryNetworkModel(cca3: String? = null, name: String? = null) = CountryNetworkModel(
        name = CountryNetworkModel.Name(
            common = name ?: "Argentina",
            official = "República Argentina",
            nativeName = mapOf(
                Pair(
                    "eng", CountryNetworkModel.NativeLanguage(
                        official = "Argentine Republic",
                        common = "Argentine"
                    )
                ),
                Pair(
                    "esp", CountryNetworkModel.NativeLanguage(
                        official = "República Argentina",
                        common = "Argentina"
                    )
                ),
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
        currencies = mapOf(Pair("ARG", CountryNetworkModel.Currency("Peso argentino", "$"))),
        idd = CountryNetworkModel.Idd("+5", listOf("4")),
        capital = listOf("Buenos Aires"),
        altSpellings = listOf("AR", "Argentina"),
        region = "America",
        subregion = "South America",
        languages = mapOf(Pair("spa", "Spanish"), Pair("que", "Quechua")),
        landlocked = false,
        borders = listOf("CHL", "BRA", "URU", "BOL", "PAR"),
        area = 7371238.1,
        demonyms = mapOf(
            Pair(
                "esp",
                CountryNetworkModel.Demonym(f = "argentina", m = "argentino")
            )
        ),
        flag = "",
        maps = CountryNetworkModel.Maps(null, null),
        population = 48000000,
        gini = mapOf(Pair("2023", 123.3)),
        fifa = "ARG",
        car = CountryNetworkModel.Car(listOf("ARG","AR"), "left"),
        timezones = listOf("TIMEZONE +3"),
        continents = listOf("Americas"),
        flags = CountryNetworkModel.Flags(null, null, null),
        coatOfArms = CountryNetworkModel.CoatOfArms(null, null),
        startOfWeek = "Monday",
        capitalInfo = CountryNetworkModel.CapitalInfo(),
        postalCode = CountryNetworkModel.PostalCode(null, null)
    )
}
