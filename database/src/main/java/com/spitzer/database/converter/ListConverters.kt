package com.spitzer.database.converter

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class ListConverters {
    @TypeConverter
    fun serializeListString(data: List<String>?) = Json.encodeToString(data)

    @TypeConverter
    fun deserializeListString(json: String): List<String>? = Json.decodeFromString(json)
}