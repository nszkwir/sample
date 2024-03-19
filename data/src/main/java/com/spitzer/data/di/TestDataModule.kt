package com.spitzer.data.di

import com.spitzer.data.fake.FakeCountriesRepository
import com.spitzer.data.fake.FakeLanguagesRepository
import com.spitzer.data.repository.CountriesRepository
import com.spitzer.data.repository.LanguagesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class],
)
abstract class TestDataModule {

    @Binds
    @Singleton
    internal abstract fun bindsLanguagesRepository(
        languagesRepository: FakeLanguagesRepository,
    ): LanguagesRepository

    @Binds
    @Singleton
    internal abstract fun bindsCountriesRepository(
        countriesRepository: FakeCountriesRepository,
    ): CountriesRepository

}
