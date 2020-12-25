package com.nourtayeb.ads.domain.usecase

import com.nourtayeb.ads.data.repository.AdRepository
import com.nourtayeb.ads.domain.entity.StringAd
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShowStringAdsUseCase @Inject constructor(
    private val adRepository: AdRepository
) {
    fun buildUseCase(): Flow<StringAd> = adRepository.getStringAds()
}