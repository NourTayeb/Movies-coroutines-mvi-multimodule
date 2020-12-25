package com.nourtayeb.ads.common.di

import com.nourtayeb.ads.data.local.db.AdDao
import com.nourtayeb.ads.data.mapper.AdMapper
import com.nourtayeb.ads.data.repository.AdRepository
import com.nourtayeb.ads.data.repository.AdRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object DataRepositoryModule {

    @Provides
    fun provideDataRepository(adMapper: AdMapper,adDao: AdDao): AdRepository {
        return AdRepositoryImp(adDao,adMapper)
    }
}