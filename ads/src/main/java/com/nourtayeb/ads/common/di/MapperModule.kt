package com.nourtayeb.ads.common.di

import com.nourtayeb.ads.data.mapper.AdMapper
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
    fun adMapper() = AdMapper()

}