package com.nourtayeb.ads.presentation.ui.interstitial

import com.nourtayeb.ads.domain.entity.ImageAd
import com.nourtayeb.ads.domain.entity.StringAd

sealed class InterstitialAdUiAction{
    object ShowImageAds : InterstitialAdUiAction()
    object ShowTextAds : InterstitialAdUiAction()
}

sealed class InterstitialAdUiState {
    data class ImageAdLoaded(val ad:ImageAd): InterstitialAdUiState()
    data class StringAdLoaded(val ad:StringAd): InterstitialAdUiState()
    object Loading : InterstitialAdUiState()
    data class Failed(val status: String? = null): InterstitialAdUiState()
    data class Exception(val exception: kotlin.Exception? = null): InterstitialAdUiState()
}