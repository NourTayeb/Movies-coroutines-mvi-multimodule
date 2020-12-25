package com.nourtayeb.movies_mvi.common.di

import android.content.Context
import androidx.room.Room
import com.nourtayeb.movies_mvi.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object LocaleStorageModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context):
            AppDatabase { return Room.databaseBuilder(context, AppDatabase::class.java, "moviesTest").build()}

    @Singleton
    @Provides
    fun provideMoviesDao(appDatabase: AppDatabase)  = appDatabase.moviesDao()

    @Singleton
    @Provides
    fun provideUserDao(appDatabase: AppDatabase)  = appDatabase.userDao()



}