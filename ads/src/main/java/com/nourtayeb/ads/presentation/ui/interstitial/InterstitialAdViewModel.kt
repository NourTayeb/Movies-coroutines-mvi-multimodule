package com.nourtayeb.ads.presentation.ui.interstitial

import android.util.Log
import androidx.lifecycle.*
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nourtayeb.ads.domain.usecase.ShowImageAdsUseCase
import com.nourtayeb.ads.domain.usecase.ShowStringAdsUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


@ExperimentalCoroutinesApi
class InterstitialAdViewModel @ViewModelInject constructor(
    val dispatcher: CoroutineDispatcher,
    val showImageAdsUseCase: ShowImageAdsUseCase,
    val showStringAdsUseCaseUseCase: ShowStringAdsUseCase
) : ViewModel() {


    fun getState(flow: Flow<InterstitialAdUiState>): LiveData<InterstitialAdUiState> {
        return flow.asLiveData(dispatcher)
    }

    fun reduce(flow: Flow<InterstitialAdUiAction>): Flow<InterstitialAdUiState> {
        return flow.distinctUntilChangedBy { it.javaClass }.flatMapMerge {
            when (it) {

                InterstitialAdUiAction.ShowImageAds ->
                    showImageAdsUseCase.buildUseCase(5)
                        .map {
                            delay(2000)
                            InterstitialAdUiState.ImageAdLoaded(it) as InterstitialAdUiState
                        }.onStart { emit(InterstitialAdUiState.Loading) }

                InterstitialAdUiAction.ShowTextAds -> {
                    showStringAdsUseCaseUseCase.buildUseCase()
                        .map { InterstitialAdUiState.StringAdLoaded(it) as InterstitialAdUiState }
                        .onStart { emit(InterstitialAdUiState.Loading) }
                }

                else -> throw IllegalArgumentException("Not supported Action")
            }
        }.catch { e -> emit(InterstitialAdUiState.Exception())  }
//            .runningReduce { accumulator, value ->
//                Log.e("reduce","value ${value.javaClass} + ,acc ${accumulator.javaClass}")
//                value
//            }
    }


}