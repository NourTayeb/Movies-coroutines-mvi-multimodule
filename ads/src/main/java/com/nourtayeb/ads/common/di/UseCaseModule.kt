package com.nourtayeb.ads.common.di

import com.nourtayeb.ads.data.repository.AdRepositoryImp
import com.nourtayeb.ads.domain.usecase.ShowImageAdsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object UseCaseModule {

    @Provides
    fun providesShowAdUsecase(adRepositoryImp: AdRepositoryImp): ShowImageAdsUseCase {
        return ShowImageAdsUseCase(adRepositoryImp)
    }



}