package com.spitzer.database.converter

import androidx.room.TypeConverter
import com.spitzer.database.model.Car
import com.spitzer.database.model.Coordinates
import com.spitzer.database.model.Currency
import com.spitzer.database.model.Demonym
import com.spitzer.database.model.Idd
import com.spitzer.database.model.Maps
import com.spitzer.database.model.Name
import com.spitzer.database.model.NameValues
import com.spitzer.database.model.PostalCode
import com.spitzer.database.model.Symbol
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class CountryDataConverters {
    @TypeConverter
    fun serializeNameValues(data: NameValues?) = Json.encodeToString(data)

    @TypeConverter
    fun deserializeNameValues(json: String): NameValues? = Json.decodeFromString(json)

    @TypeConverter
    fun serializeName(data: Name?) = Json.encodeToString(data)

    @TypeConverter
    fun deserializeName(json: String): Name? = Json.decodeFromString(json)

    @TypeConverter
    fun serializeCurrency(data: Currency?) = Json.encodeToString(data)

    @TypeConverter
    fun deserializeCurrency(json: String): Currency? = Json.decodeFromString(json)

    @TypeConverter
    fun serializeIdd(data: Idd?) = Json.encodeToString(data)

    @TypeConverter
    fun deserializeIdd(json: String): Idd? = Json.decodeFromString(json)

    @TypeConverter
    fun serializeDemonym(data: Demonym?) = Json.encodeToString(data)

    @TypeConverter
    fun deserializeDemonym(json: String): Demonym? = Json.decodeFromString(json)

    @TypeConverter
    fun serializeMaps(data: Maps?) = Json.encodeToString(data)

    @TypeConverter
    fun deserializeMaps(json: String): Maps? = Json.decodeFromString(json)

    @TypeConverter
    fun serializeCar(data: Car?) = Json.encodeToString(data)

    @TypeConverter
    fun deserializeCar(json: String): Car? = Json.decodeFromString(json)

    @TypeConverter
    fun serializeSymbol(data: Symbol?) = Json.encodeToString(data)

    @TypeConverter
    fun deserializeSymbol(json: String): Symbol? = Json.decodeFromString(json)

    @TypeConverter
    fun serializePostalCode(data: PostalCode?) = Json.encodeToString(data)

    @TypeConverter
    fun deserializePostalCode(json: String): PostalCode? = Json.decodeFromString(json)

    @TypeConverter
    fun serializeCoordinates(data: Coordinates?) = Json.encodeToString(data)

    @TypeConverter
    fun deserializeCoordinates(json: String): Coordinates? = Json.decodeFromString(json)

    // Maps
    @TypeConverter
    fun serializeMapStringCurrency(value: Map<String, Currency>?): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun deserializeMapStringCurrency(value: String?): Map<String, Currency>? {
        if (value == null) {
            return null
        }
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun serializeMapStringDemonym(value: Map<String, Demonym>?): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun deserializeMapStringDemonym(value: String?): Map<String, Demonym>? {
        if (value == null) {
            return null
        }
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun serializeMapStringNameValues(value: Map<String, NameValues>?): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun deserializeMapStringNameValues(value: String?): Map<String, NameValues>? {
        if (value == null) {
            return null
        }
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun serializeMapStringDouble(value: Map<String, Double>?): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun deserializeMapStringDouble(value: String?): Map<String, Double>? {
        if (value == null) {
            return null
        }
        return Json.decodeFromString(value)
    }
}
