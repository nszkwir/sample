package com.spitzer.database.di

import android.content.Context
import androidx.room.Room
import com.spitzer.database.MainDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesMainDatabase(
        @ApplicationContext context: Context,
    ): MainDatabase = Room.databaseBuilder(
        context,
        MainDatabase::class.java,
        "main-database",
    ).build()
}
