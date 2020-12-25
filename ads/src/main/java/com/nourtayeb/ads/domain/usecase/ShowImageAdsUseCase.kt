package com.nourtayeb.ads.domain.usecase

import com.nourtayeb.ads.domain.entity.ImageAd
import com.nourtayeb.ads.data.repository.AdRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class ShowImageAdsUseCase @Inject constructor(
    private val adRepository: AdRepository
) {
    fun buildUseCase(numOfAds:Int): Flow<ImageAd> = adRepository.getImageAds().take(numOfAds).buffer()
}