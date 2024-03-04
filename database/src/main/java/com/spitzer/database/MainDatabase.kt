package com.spitzer.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.spitzer.database.converter.CountryDataConverters
import com.spitzer.database.converter.InstantConverter
import com.spitzer.database.converter.ListConverters
import com.spitzer.database.dao.CountryDao
import com.spitzer.database.dao.FakeRemoteCountryDao
import com.spitzer.database.model.CountryEntity
import com.spitzer.database.model.FakeRemoteCountryEntity

@Database(
    entities = [
        CountryEntity::class,
        FakeRemoteCountryEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(
    ListConverters::class,
    InstantConverter::class,
    CountryDataConverters::class
)
internal abstract class MainDatabase : RoomDatabase() {
    abstract fun fakeRemoteCountries(): FakeRemoteCountryDao
    abstract fun countries(): CountryDao

}
