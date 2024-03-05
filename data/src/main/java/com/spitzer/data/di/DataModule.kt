package com.spitzer.data.di

import com.spitzer.data.repository.CountriesRepository
import com.spitzer.data.repository.FakeCountriesRemoteRepository
import com.spitzer.data.repository.LanguagesRepository
import com.spitzer.data.repository.countries.CountriesRepositoryImpl
import com.spitzer.data.repository.fake.FakeCountriesRemoteRepositoryImpl
import com.spitzer.data.repository.fake.FakeLanguagesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    internal abstract fun bindsFakeCountriesRemoteRepository(
        countriesRepository: FakeCountriesRemoteRepositoryImpl,
    ): FakeCountriesRemoteRepository

    @Binds
    internal abstract fun bindsFakeLanguagesRemoteRepository(
        languagesRepository: FakeLanguagesRepositoryImpl,
    ): LanguagesRepository

    @Binds
    internal abstract fun bindsCountriesRepository(
        countriesRepository: CountriesRepositoryImpl,
    ): CountriesRepository

}
