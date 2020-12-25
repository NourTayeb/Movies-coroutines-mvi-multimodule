package com.nourtayeb.ads.presentation.ui

import android.content.Context
import android.os.Build
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.liveData
import androidx.test.core.app.ApplicationProvider
import com.nourtayeb.ads.presentation.common.base.ActivityBaseTest
import com.nourtayeb.ads.comon.dummyImageAd
import com.nourtayeb.ads.comon.dummyStringAd
import com.nourtayeb.ads.presentation.ui.interstitial.*
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.spyk
import io.mockk.verify
import kotlinx.android.synthetic.main.activity_interstitial_ad.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class InterstitialAdActivityTest : ActivityBaseTest() {

    lateinit var activity: InterstitialAdActivity
    @RelaxedMockK
    lateinit var viewModel: InterstitialAdViewModel

    lateinit var context: Context

    @Before
    fun init() {
        activity = spyk(InterstitialAdActivity())
        every { activity.viewModel } returns viewModel
        context = ApplicationProvider.getApplicationContext<Context>()
        every { activity.close } returns ImageView(context)
        every { activity.image } returns ImageView(context)
        every { activity.textAd } returns TextView(context)
        every { activity.loadImageAds } returns Button(context)
        every { activity.loadTextAds } returns Button(context)
        every { activity.hideLoading() } returns Unit
        every { activity.showLoading() } returns Unit
        every { activity.showToast(any()) } returns Unit
    }


    @Test
    fun `ImageAdLoaded state calls activity hideLoading and showImageInImageView`() {
        runBlockingTest {
            val state = InterstitialAdUiState.ImageAdLoaded(dummyImageAd) as InterstitialAdUiState
            every { viewModel.getState(any()) } returns liveData { emit(state) }
            viewModel.getState(flowOf(state)).observe(lifecycleOwner, activity.getStateObserver())
            verify { activity.showImageInImageview(any(), dummyImageAd.image) }
            verify { activity.hideLoading() }
        }
    }

    @Test
    fun `ImageAdLoaded state never calls activity showLoading or showToast`() {
        runBlockingTest {
            val state = InterstitialAdUiState.ImageAdLoaded(dummyImageAd) as InterstitialAdUiState
            every { viewModel.getState(any()) } returns liveData { emit(state) }
            viewModel.getState(flowOf(state)).observe(lifecycleOwner, activity.getStateObserver())
            verify (exactly = 0) { activity.showLoading() }
            verify (exactly = 0){ activity.showToast(any()) }
        }
    }

    @Test
    fun `StringAdLoaded state calls activity hideLoading and showTextInTextView`() {
        runBlockingTest {
            val state = InterstitialAdUiState.StringAdLoaded(dummyStringAd) as InterstitialAdUiState
            every { viewModel.getState(any()) } returns liveData { emit(state) }
            viewModel.getState(flowOf(state)).observe(lifecycleOwner, activity.getStateObserver())
            verify { activity.showTextInTextView(any(), dummyStringAd.name) }
            verify { activity.hideLoading() }
        }
    }

    @Test
    fun `StringAdLoaded state never calls activity showLoading or showToast`() {
        runBlockingTest {
            val state = InterstitialAdUiState.StringAdLoaded(dummyStringAd) as InterstitialAdUiState
            every { viewModel.getState(any()) } returns liveData { emit(state) }
            viewModel.getState(flowOf(state)).observe(lifecycleOwner, activity.getStateObserver())
            verify (exactly = 0) { activity.showLoading() }
            verify (exactly = 0){ activity.showToast(any()) }
        }
    }
    @Test
    fun `Loading state calls activity showLoading`() {
        runBlockingTest {
            val state = InterstitialAdUiState.Loading as InterstitialAdUiState
            every { viewModel.getState(any()) } returns liveData { emit(state) }
            viewModel.getState(flowOf(state)).observe(lifecycleOwner, activity.getStateObserver())
            verify { activity.showLoading() }
        }
    }

    @Test
    fun `Exception state calls activity showToast and hideLoading`() {
        runBlockingTest {
            val state = InterstitialAdUiState.Exception() as InterstitialAdUiState
            every { viewModel.getState(any()) } returns liveData { emit(state) }
            viewModel.getState(flowOf(state)).observe(lifecycleOwner, activity.getStateObserver())
            verify { activity.showToast(any()) }
            verify { activity.hideLoading() }
        }
    }



}