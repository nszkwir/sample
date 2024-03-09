package com.spitzer.ui.feature.countryDetails

import com.spitzer.model.data.Coordinates
import com.spitzer.model.data.CountryModel
import com.spitzer.model.data.ISOLanguage

data class CountryDetailsUiState(
    val country: CountryDetailsUiModel? = null,
    val isLoading: Boolean = true,
    val isError: Boolean = false,
)

// TODO create mapper with injected languages data source
fun CountryModel.mapToCountryDetailsModel(languages: Map<String, ISOLanguage>) =
    CountryDetailsUiModel(
        commonName = this.name.common,
        officialName = this.name.official,
        capitalsNames = this.capital,
        flagUrl = this.flags.svg,
        coatOfArmsUrl = this.coatOfArms.svg,
        population = this.population,
        area = this.area,
        officialLanguages = this.languages.mapNotNull { languages[it]?.language },
        borderCountries = this.borders,
        continents = this.continents,
        region = this.region,
        subregion = this.subregion,
        badges = mutableListOf<CountryDetailsUiModel.Badge>().apply {
            this.add(CountryDetailsUiModel.Badge.UnitedNations(this@mapToCountryDetailsModel.isUNMember))
            this.addAll(
                this@mapToCountryDetailsModel.currencies.mapNotNull {
                    val name = it.value.name ?: ""
                    val symbol = it.value.symbol ?: ""
                    if (name.isNotEmpty() || symbol.isNotEmpty()) {
                        CountryDetailsUiModel.Badge.Currency(
                            code = it.key,
                            name = name,
                            symbol = symbol
                        )
                    } else {
                        null
                    }
                }
            )
            this.addAll(
                this@mapToCountryDetailsModel.continents.map {
                    CountryDetailsUiModel.Badge.Continent(
                        when (it.lowercase()) {
                            "europe" -> CountryDetailsUiModel.Badge.Continent.Continent.EUROPE
                            "asia" -> CountryDetailsUiModel.Badge.Continent.Continent.ASIA
                            "africa" -> CountryDetailsUiModel.Badge.Continent.Continent.AFRICA
                            "north america" -> CountryDetailsUiModel.Badge.Continent.Continent.NORTH_AMERICA
                            "south america" -> CountryDetailsUiModel.Badge.Continent.Continent.SOUTH_AMERICA
                            "oceania" -> CountryDetailsUiModel.Badge.Continent.Continent.OCEANIA
                            else -> CountryDetailsUiModel.Badge.Continent.Continent.UNDETERMINED
                        }
                    )
                }
            )
            this.addAll(
                this@mapToCountryDetailsModel.idd.suffixes.map {
                    CountryDetailsUiModel.Badge.Phone("${this@mapToCountryDetailsModel.idd.root}$it")
                }
            )
        },
        startOfWeek = this.startOfWeek,
        coordinates = this.coordinates,
        capitalCoordinates = this.capitalCoordinates
    )

data class CountryDetailsUiModel(
    val commonName: String,
    val officialName: String?,
    val capitalsNames: List<String>?,
    val flagUrl: String?,
    val coatOfArmsUrl: String?,
    val population: Long,
    val area: Double,
    val officialLanguages: List<String>,
    val borderCountries: List<String>,
    val continents: List<String>,
    val region: String,
    val subregion: String?,
    val badges: List<Badge>,
    val startOfWeek: String,
    val coordinates: Coordinates?,
    val capitalCoordinates: Coordinates?,
) {
    sealed interface Badge {
        data class UnitedNations(
            val isMember: Boolean
        ) : Badge

        data class Currency(
            val code: String,
            val symbol: String,
            val name: String
        ) : Badge

        data class Continent(
            val continent: Continent
        ) : Badge {
            enum class Continent {
                EUROPE, NORTH_AMERICA, SOUTH_AMERICA, ASIA, OCEANIA, AFRICA, ANTARCTICA, UNDETERMINED
            }
        }

        data class Phone(
            val code: String,
        ) : Badge
    }
}
