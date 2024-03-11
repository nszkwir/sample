package com.spitzer.network.com.spitzer.network.testing

import com.spitzer.network.model.CountryNetworkModel

object TestCountryNetworkModelProvider {
    fun getCountryNetworkModel(cca3: String? = null, name: String? = null): CountryNetworkModel {
        return CountryNetworkModel(
            name = CountryNetworkModel.Name(
                common = name ?: "Argentina",
                official = "República Argentina",
                nativeName = mapOf(
                    Pair(
                        "eng", CountryNetworkModel.NativeLanguage(
                            official = "Argentine Republic",
                            common = "Argentine"
                        )
                    )
                ),
            ),
            cca2 = "AR",
            cca3 = cca3 ?: "ARG",
            tld = emptyList(),
            ccn3 = null,
            cioc = null,
            independent = true,
            status = "",
            isUNMember = true,
            currencies = mapOf(Pair("ARG", CountryNetworkModel.Currency("Peso argentino", "$"))),
            idd = CountryNetworkModel.Idd("+54", emptyList()),
            capital = listOf("Buenos Aires"),
            altSpellings = emptyList(),
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
            car = CountryNetworkModel.Car(listOf("ARG"), "left"),
            timezones = emptyList(),
            continents = listOf("Americas"),
            flags = CountryNetworkModel.Flags(null, null, null),
            coatOfArms = CountryNetworkModel.CoatOfArms(null, null),
            startOfWeek = "Monday",
            capitalInfo = CountryNetworkModel.CapitalInfo(),
            postalCode = CountryNetworkModel.PostalCode(null, null)
        )
    }
}
