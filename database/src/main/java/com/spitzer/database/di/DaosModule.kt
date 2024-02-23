package com.spitzer.database.di

import com.spitzer.database.MainDatabase
import com.spitzer.database.dao.CountryDao
import com.spitzer.database.dao.FakeRemoteCountryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun providesFakeRemoteCountriesDao(
        database: MainDatabase,
    ): FakeRemoteCountryDao = database.fakeRemoteCountries()

    @Provides
    fun providesCountriesDao(
        database: MainDatabase,
    ): CountryDao = database.countries()

}
