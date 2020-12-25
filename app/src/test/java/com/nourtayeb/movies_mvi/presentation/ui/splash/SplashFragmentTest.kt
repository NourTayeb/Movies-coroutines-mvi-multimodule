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
import org.robolectric.RobolectricTestRunner
import androidx.test.core.app.ApplicationProvider
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class SplashFragmentTest : ActivityBaseTest() {
    lateinit var fragment: SplashFragment

    @RelaxedMockK
    lateinit var viewModel: SplashViewModel

    lateinit var context: Context

    @Before
    fun init() {
        fragment = spyk(SplashFragment())
        every { fragment.viewModel } returns viewModel
        context = ApplicationProvider.getApplicationContext<Context>()
    }

    @Test
    fun `SuccessUiState calls startMovies`() {
        val userId = 2
        every { fragment.startMovies() } returns Unit
        every { viewModel.state } returns MutableLiveData(
                SplashMoviesUiState.Success
        )
        viewModel.performAction(SplashUiAction.Login(userId))
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify { fragment.startMovies() }
    }

    @Test
    fun `SuccessUiState doesnt showToast`() {
        val userId = 2
        every { fragment.startMovies() } returns Unit
        every { viewModel.state } returns MutableLiveData(
                SplashMoviesUiState.Success
        )
        viewModel.performAction(SplashUiAction.Login(userId))
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify(exactly = 0) { fragment.showToast(any()) }
    }

    @Test
    fun `FailedUiState calls showToast(R-string-authorization_failed)`() {
        every { fragment.getString(R.string.authorization_failed) } returns context.getString(R.string.authorization_failed)
        val userId = 2
        val message = context.getString(R.string.authorization_failed)
        every { fragment.showToast(message) } returns Unit
        every { viewModel.state } returns MutableLiveData(
                SplashMoviesUiState.Failed()
        )
        viewModel.performAction(SplashUiAction.Login(userId))
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify { fragment.showToast(message) }
    }

    @Test
    fun `ExceptionUiState calls showToast(R-string-some_error_occurred)`() {
        every { fragment.getString(R.string.some_error_occurred) } returns context.getString(R.string.some_error_occurred)
        val userId = 2
        val message = context.getString(R.string.some_error_occurred)
        every { fragment.showToast(message) } returns Unit
        every { viewModel.state } returns MutableLiveData(
                SplashMoviesUiState.Exception()
        )
        viewModel.performAction(SplashUiAction.Login(userId))
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify { fragment.showToast(message) }
    }

    @Test
    fun `FailedUiState doesnt call showMovies`() {
        every { fragment.getString(R.string.authorization_failed) } returns context.getString(R.string.authorization_failed)
        val userId = 2
        val message = context.getString(R.string.authorization_failed)
        every { fragment.showToast(message) } returns Unit
        every { viewModel.state } returns MutableLiveData(
                SplashMoviesUiState.Failed()
        )
        viewModel.performAction(SplashUiAction.Login(userId))
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify(exactly = 0) { fragment.startMovies() }
    }

    @Test
    fun `ExceptionUiState doesnt call showMovies`() {
        every { fragment.getString(R.string.some_error_occurred) } returns context.getString(R.string.some_error_occurred)
        val userId = 2
        val message = context.getString(R.string.some_error_occurred)
        every { fragment.showToast(message) } returns Unit
        every {viewModel.state } returns MutableLiveData(
                SplashMoviesUiState.Exception()
        )
        viewModel.performAction(SplashUiAction.Login(userId))
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify(exactly = 0) { fragment.startMovies() }
    }
}