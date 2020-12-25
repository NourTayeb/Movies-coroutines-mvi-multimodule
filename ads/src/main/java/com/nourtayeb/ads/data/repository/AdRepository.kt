package com.nourtayeb.ads.data.repository

import com.nourtayeb.ads.domain.entity.ImageAd
import com.nourtayeb.ads.domain.entity.StringAd
import kotlinx.coroutines.flow.Flow

interface AdRepository {
    fun getImageAds():Flow<ImageAd>
    fun getStringAds():Flow<StringAd>
}