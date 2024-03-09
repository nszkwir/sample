package com.spitzer.data.di

import com.spitzer.data.repository.CountriesRepository
import com.spitzer.data.repository.LanguagesRepository
import com.spitzer.data.repository.countries.CountriesRepositoryImpl
import com.spitzer.data.repository.languages.LanguagesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    internal abstract fun bindsLanguagesRepository(
        languagesRepository: LanguagesRepositoryImpl,
    ): LanguagesRepository

    @Binds
    @Singleton
    internal abstract fun bindsCountriesRepository(
        countriesRepository: CountriesRepositoryImpl,
    ): CountriesRepository

}
