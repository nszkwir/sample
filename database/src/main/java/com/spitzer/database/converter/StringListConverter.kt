package com.spitzer.database.converter

import androidx.room.TypeConverter

internal class StringListConverter {
    companion object{
        private const val separator = "]#<{-"
    }
    @TypeConverter
    fun stringListToString(list: List<String>?): String? =
        list?.joinToString(separator)

    @TypeConverter
    fun stringToStringList(string: String?): List<String>? =
        string?.split(separator)
}
