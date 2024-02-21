package com.spitzer.data.di

import com.spitzer.data.repository.CountriesRepository
import com.spitzer.data.repository.fake.FakeCountriesRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsCountriesRepository(
        countriesRepository: FakeCountriesRemoteRepository,
    ): CountriesRepository

}
