package com.spitzer.data.mapper

import com.spitzer.database.model.Car
import com.spitzer.database.model.Coordinates
import com.spitzer.database.model.CountryEntity
import com.spitzer.database.model.Currency
import com.spitzer.database.model.Demonym
import com.spitzer.database.model.FakeRemoteCountryEntity
import com.spitzer.database.model.Idd
import com.spitzer.database.model.Maps
import com.spitzer.database.model.Name
import com.spitzer.database.model.NameValues
import com.spitzer.database.model.PostalCode
import com.spitzer.database.model.Symbol
import com.spitzer.database.model.asDataModel
import com.spitzer.model.data.CountryModel
import javax.inject.Inject

class CountryModelMapper @Inject constructor(

) {
    /**
     * DataModel <-> DB Entity
     * CountryModel <-> CountryEntity
     */
    fun mapEntityToDataModel(entity: CountryEntity): CountryModel {
        return entity.asDataModel()
    }

    fun mapEntitiesToDataModelMap(entities: List<CountryEntity>): Map<String, CountryModel> {
        return entities.map { mapEntityToDataModel(it) }
            .associateBy({ it.cca3 }, { it })
    }

    fun mapDataModelToEntity(dataModel: CountryModel): CountryEntity {
        return dataModel.asCountryEntity()
    }

    fun mapDataModelListToEntities(dataModels: List<CountryModel>): List<CountryEntity> {
        return dataModels.map { this.mapDataModelToEntity(it) }
    }

    /**
     * DataModel <-> RemoteModel
     * CountryModel <-> FakeRemoteCountryEntity
     * Note: We use a DB to fake the Remote
     */
    fun mapDataModelToFakeRemoteEntity(dataModel: CountryModel): FakeRemoteCountryEntity {
        return dataModel.asFakeRemoteCountryEntity()
    }

    fun mapDataModelListToFakeRemoteEntities(dataModels: List<CountryModel>): List<FakeRemoteCountryEntity> {
        return dataModels.map { this.mapDataModelToFakeRemoteEntity(it) }
    }

    fun mapFakeRemoteEntityToDataModel(entity: FakeRemoteCountryEntity): CountryModel {
        return entity.asDataModel()
    }

    /**
     * RemoteModel <-> DataModel <-> DB Entity
     * FakeRemoteCountryEntity <-> CountryModel <-> CountryEntity
     * Defined this way to ensure the same mapping logic
     */
    fun mapFakeRemoteEntityListToEntities(dataModels: List<FakeRemoteCountryEntity>): List<CountryEntity> {
        return dataModels
            .map { this.mapFakeRemoteEntityToDataModel(it) }
            .map { mapDataModelToEntity(it) }
    }

    fun mapFakeRemoteEntitiesToDataModelMap(entities: List<FakeRemoteCountryEntity>): Map<String, CountryModel> {
        return entities.map { mapFakeRemoteEntityToDataModel(it) }
            .associateBy({ it.cca3 }, { it })
    }
}

fun CountryModel.asCountryEntity(): CountryEntity {
    return CountryEntity(
        name = Name(
            common = this.name.common,
            official = this.name.official,
            nativeName = this.name.nativeName.mapValues {
                NameValues(
                    official = it.value.official,
                    common = it.value.common
                )
            }
        ),
        tags = this.tags,
        tld = this.tld,
        cca2 = this.cca2,
        ccn3 = this.ccn3,
        cca3 = this.cca3,
        cioc = this.cioc,
        independent = this.independent,
        status = this.status,
        isUNMember = this.isUNMember,
        currencies = this.currencies.mapValues {
            Currency(
                name = it.value.name,
                symbol = it.value.symbol
            )
        },
        idd = Idd(root = this.idd.root, suffixes = this.idd.suffixes),
        capital = this.capital,
        altSpellings = this.altSpellings,
        region = this.region,
        subregion = this.subregion,
        languages = this.languages,
        countryNameTranslations = this.countryNameTranslations.mapValues {
            NameValues(
                official = it.value.official,
                common = it.value.common
            )
        },
        coordinates = mapToEntityCoordinates(this.coordinates),
        landlocked = this.landlocked,
        borders = this.borders,
        area = this.area,
        demonyms = this.demonyms.mapValues {
            Demonym(
                f = it.value.f,
                m = it.value.m
            )
        },
        flag = this.flag,
        maps = Maps(
            googleMapsLink = this.maps.googleMapsLink,
            openStreetMapsLink = this.maps.openStreetMapsLink
        ),
        population = this.population,
        gini = this.gini,
        fifa = this.fifa,
        car = Car(
            signs = this.car.signs,
            drivingSide = when (this.car.drivingSide) {
                CountryModel.Car.DrivingSide.LEFT -> "left"
                CountryModel.Car.DrivingSide.RIGHT -> "right"
                else -> null
            }
        ),
        timezones = this.timezones,
        continents = this.continents,
        flags = Symbol(
            svg = this.flags.svg,
            png = this.flags.png,
            alt = this.flags.alt
        ),
        coatOfArms = Symbol(
            svg = this.coatOfArms.svg,
            png = this.coatOfArms.png,
            alt = this.coatOfArms.alt
        ),
        startOfWeek = this.startOfWeek,
        capitalCoordinates = mapToEntityCoordinates(this.capitalCoordinates),
        postalCode = PostalCode(
            format = this.postalCode?.format,
            regex = this.postalCode?.regex
        ),
        publishDate = this.publishDate,
        updateDate = this.updateDate
    )
}

private fun mapToEntityCoordinates(coordinates: com.spitzer.model.data.Coordinates?): Coordinates? {
    return if (coordinates == null) null
    else Coordinates(latitude = coordinates.latitude, longitude = coordinates.longitude)
}

fun CountryModel.asFakeRemoteCountryEntity(): FakeRemoteCountryEntity {
    return FakeRemoteCountryEntity(
        name = Name(
            common = this.name.common,
            official = this.name.official,
            nativeName = this.name.nativeName.mapValues {
                NameValues(
                    official = it.value.official,
                    common = it.value.common
                )
            }
        ),
        tags = this.tags,
        tld = this.tld,
        cca2 = this.cca2,
        ccn3 = this.ccn3,
        cca3 = this.cca3,
        cioc = this.cioc,
        independent = this.independent,
        status = this.status,
        isUNMember = this.isUNMember,
        currencies = this.currencies.mapValues {
            Currency(
                name = it.value.name,
                symbol = it.value.symbol
            )
        },
        idd = Idd(root = this.idd.root, suffixes = this.idd.suffixes),
        capital = this.capital,
        altSpellings = this.altSpellings,
        region = this.region,
        subregion = this.subregion,
        languages = this.languages,
        countryNameTranslations = this.countryNameTranslations.mapValues {
            NameValues(
                official = it.value.official,
                common = it.value.common
            )
        },
        coordinates = mapToEntityCoordinates(this.coordinates),
        landlocked = this.landlocked,
        borders = this.borders,
        area = this.area,
        demonyms = this.demonyms.mapValues {
            Demonym(
                f = it.value.f,
                m = it.value.m
            )
        },
        flag = this.flag,
        maps = Maps(
            googleMapsLink = this.maps.googleMapsLink,
            openStreetMapsLink = this.maps.openStreetMapsLink
        ),
        population = this.population,
        gini = this.gini,
        fifa = this.fifa,
        car = Car(
            signs = this.car.signs,
            drivingSide = when (this.car.drivingSide) {
                CountryModel.Car.DrivingSide.LEFT -> "left"
                CountryModel.Car.DrivingSide.RIGHT -> "right"
                else -> null
            }
        ),
        timezones = this.timezones,
        continents = this.continents,
        flags = Symbol(
            svg = this.flags.svg,
            png = this.flags.png,
            alt = this.flags.alt
        ),
        coatOfArms = Symbol(
            svg = this.coatOfArms.svg,
            png = this.coatOfArms.png,
            alt = this.coatOfArms.alt
        ),
        startOfWeek = this.startOfWeek,
        capitalCoordinates = mapToEntityCoordinates(this.capitalCoordinates),
        postalCode = PostalCode(
            format = this.postalCode?.format,
            regex = this.postalCode?.regex
        ),
        publishDate = this.publishDate,
        updateDate = this.updateDate
    )
}
