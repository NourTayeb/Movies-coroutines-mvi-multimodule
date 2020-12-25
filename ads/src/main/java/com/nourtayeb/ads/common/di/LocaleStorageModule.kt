package com.nourtayeb.ads.common.di

import android.content.Context
import androidx.room.Room
import com.nourtayeb.ads.data.local.db.AppDatabase
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
            AppDatabase { return Room.databaseBuilder(context, AppDatabase::class.java, "ads").build()}

    @Singleton
    @Provides
    fun provideMoviesDao(appDatabase: AppDatabase)  = appDatabase.adDao()




}