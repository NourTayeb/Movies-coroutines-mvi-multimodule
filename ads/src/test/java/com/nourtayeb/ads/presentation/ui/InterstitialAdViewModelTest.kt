package com.nourtayeb.ads.presentation.ui

import com.nourtayeb.ads.domain.usecase.ShowImageAdsUseCase
import com.nourtayeb.ads.domain.usecase.ShowStringAdsUseCase
import com.nourtayeb.ads.presentation.common.base.ViewModelBaseTest
import com.nourtayeb.ads.comon.fiveImageAds
import com.nourtayeb.ads.comon.stringAds
import com.nourtayeb.ads.presentation.ui.interstitial.InterstitialAdUiAction
import com.nourtayeb.ads.presentation.ui.interstitial.InterstitialAdUiState
import com.nourtayeb.ads.presentation.ui.interstitial.InterstitialAdViewModel
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class InterstitialAdViewModelTest : ViewModelBaseTest() {
    lateinit var viewModel: InterstitialAdViewModel

    @RelaxedMockK
    lateinit var showImageAdsUseCase: ShowImageAdsUseCase

    @RelaxedMockK
    lateinit var showStringAdsUseCase: ShowStringAdsUseCase

    @Before
    fun init() {
        viewModel =
            InterstitialAdViewModel(testDispatcher, showImageAdsUseCase, showStringAdsUseCase)
    }

    @Test
    fun `ShowImageAds UiAction emits Loading then 5 ImageAdLoaded`() = runBlockingTest {
        every { showImageAdsUseCase.buildUseCase(5) } returns fiveImageAds.asFlow()
        val list = mutableListOf<InterstitialAdUiState>()
        val flow = flow {
            emit(InterstitialAdUiAction.ShowImageAds)
        }
        viewModel.reduce(flow).collect {
            list.add(it)
        }
        val expectedResult = mutableListOf<InterstitialAdUiState>()
        expectedResult.add(InterstitialAdUiState.Loading)
        expectedResult.addAll(fiveImageAds.map { InterstitialAdUiState.ImageAdLoaded(it) })
        Assert.assertEquals(list,expectedResult)
    }
    @Test
    fun `ShowTextAds UiAction emits Loading then buildUseCase result`() = runBlockingTest {
        every { showStringAdsUseCase.buildUseCase() } returns stringAds.asFlow()
        val list = mutableListOf<InterstitialAdUiState>()
        val flow = flow {
            emit(InterstitialAdUiAction.ShowTextAds)
        }
        viewModel.reduce(flow).collect {
            list.add(it)
        }
        val expectedResult = mutableListOf<InterstitialAdUiState>()
        expectedResult.add(InterstitialAdUiState.Loading)
        expectedResult.addAll(stringAds.map { InterstitialAdUiState.StringAdLoaded(it) })
        Assert.assertEquals(list,expectedResult)
    }
    @Test
    fun `Thrown Exception emits Loading then Exception`() = runBlockingTest {
        val list = mutableListOf<InterstitialAdUiState>()
        val flow = flow<InterstitialAdUiAction> {
            emit(InterstitialAdUiAction.ShowTextAds)
            3/0
        }
        viewModel.reduce(flow).collect {
            list.add(it)
        }
        val expectedResult = mutableListOf<InterstitialAdUiState>()
        expectedResult.add(InterstitialAdUiState.Loading)
        expectedResult.add(InterstitialAdUiState.Exception())
        Assert.assertEquals(list,expectedResult)
    }
}