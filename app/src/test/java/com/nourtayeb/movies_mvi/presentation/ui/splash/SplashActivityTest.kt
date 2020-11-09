package com.nourtayeb.movies_mvi.presentation.ui.splash

import android.content.Context
import android.os.Build
import androidx.lifecycle.MutableLiveData
import com.nourtayeb.movies_mvi.R
import com.nourtayeb.movies_mvi.common.base.ActivityBaseTest
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import androidx.test.core.app.ApplicationProvider
import org.robolectric.annotation.Config
@RunWith(RobolectricTestRunner::class)
@Config(sdk= [Build.VERSION_CODES.P])
class SplashActivityTest :ActivityBaseTest() {



    lateinit var activity: SplashActivity

    @RelaxedMockK
    lateinit var viewModel: SplashViewModel

    lateinit var context: Context
    @Before
    fun init() {
        activity = spyk(Robolectric.setupActivity(SplashActivity::class.java))
        every { activity.viewModel } returns viewModel
        context = ApplicationProvider.getApplicationContext<Context>()
    }

    @Test
    fun `SuccessUiState calls startMovies`() {
        val userId = 2
        every { activity.startMovies() } returns Unit
        every { viewModel.performAction(SplashUiAction.Login(userId)) } returns MutableLiveData(
            SplashMoviesUiState.Success
        )
        viewModel.performAction(SplashUiAction.Login(userId))
            .observe(lifecycleOwner, activity.getStateObserver())
        verify { activity.startMovies() }
    }
    @Test
    fun `SuccessUiState doesnt showToast`() {
        val userId = 2
        every { activity.startMovies() } returns Unit
        every { viewModel.performAction(SplashUiAction.Login(userId)) } returns MutableLiveData(
            SplashMoviesUiState.Success
        )
        viewModel.performAction(SplashUiAction.Login(userId))
            .observe(lifecycleOwner, activity.getStateObserver())
        verify (exactly = 0){ activity.showToast(any()) }
    }
    @Test
    fun `FailedUiState calls showToast(R-string-authorization_failed)`() {
        every { activity.getString(R.string.authorization_failed) } returns context.getString(R.string.authorization_failed)
        val userId = 2
        val message = context.getString(R.string.authorization_failed)
        every { activity.showToast(message) } returns Unit
        every { viewModel.performAction(SplashUiAction.Login(userId)) } returns MutableLiveData(
            SplashMoviesUiState.Failed()
        )
        viewModel.performAction(SplashUiAction.Login(userId))
            .observe(lifecycleOwner, activity.getStateObserver())
        verify { activity.showToast(message) }
    }
    @Test
    fun `ExceptionUiState calls showToast(R-string-some_error_occurred)`() {
        every { activity.getString(R.string.some_error_occurred) } returns context.getString(R.string.some_error_occurred)
        val userId = 2
        val message = context.getString(R.string.some_error_occurred)
        every { activity.showToast(message) } returns Unit
        every { viewModel.performAction(SplashUiAction.Login(userId)) } returns MutableLiveData(
            SplashMoviesUiState.Exception()
        )
        viewModel.performAction(SplashUiAction.Login(userId))
            .observe(lifecycleOwner, activity.getStateObserver())
        verify { activity.showToast(message) }
    }
    @Test
    fun `FailedUiState doesnt call showMovies`() {
        every { activity.getString(R.string.authorization_failed) } returns context.getString(R.string.authorization_failed)
        val userId = 2
        val message = context.getString(R.string.authorization_failed)
        every { activity.showToast(message) } returns Unit
        every { viewModel.performAction(SplashUiAction.Login(userId)) } returns MutableLiveData(
            SplashMoviesUiState.Failed()
        )
        viewModel.performAction(SplashUiAction.Login(userId))
            .observe(lifecycleOwner, activity.getStateObserver())
        verify (exactly = 0){ activity.startMovies() }
    }
    @Test
    fun `ExceptionUiState doesnt call showMovies`() {
        every { activity.getString(R.string.some_error_occurred) } returns context.getString(R.string.some_error_occurred)
        val userId = 2
        val message = context.getString(R.string.some_error_occurred)
        every { activity.showToast(message) } returns Unit
        every { viewModel.performAction(SplashUiAction.Login(userId)) } returns MutableLiveData(
            SplashMoviesUiState.Exception()
        )
        viewModel.performAction(SplashUiAction.Login(userId))
            .observe(lifecycleOwner, activity.getStateObserver())
        verify (exactly = 0){ activity.startMovies() }
    }
}