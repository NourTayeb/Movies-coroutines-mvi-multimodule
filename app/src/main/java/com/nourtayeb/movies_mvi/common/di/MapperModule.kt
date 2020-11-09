package com.nourtayeb.movies_mvi.common.di

import com.nourtayeb.movies_mvi.data.mapper.MovieMapper
import com.nourtayeb.movies_mvi.data.mapper.UserMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun movieMapper() = MovieMapper()
    @Provides
    @Singleton
    fun userMapper() = UserMapper()

}