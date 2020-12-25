package com.nourtayeb.movies_mvi.presentation.ui.splash

import androidx.lifecycle.Observer
import com.nourtayeb.movies_mvi.common.base.ViewModelBaseTest
import com.nourtayeb.movies_mvi.domain.UseCaseResult
import com.nourtayeb.movies_mvi.domain.entity.User
import com.nourtayeb.movies_mvi.domain.usecase.LoginUseCase
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SplashViewModelTest :ViewModelBaseTest(){


    @RelaxedMockK
    lateinit var  loginUseCase: LoginUseCase


    lateinit var viewModel:SplashViewModel
    @Before
    fun init(){
        viewModel = SplashViewModel(testDispatcher,loginUseCase)
    }

    @Test
    fun `success login returns Loading then SuccessUiState` (){
        val userId =1
        coEvery { loginUseCase.buildUseCase(User(userId)) } returns UseCaseResult.Success()
         viewModel.performAction(SplashUiAction.Login(userId))
        val list= mutableListOf<SplashMoviesUiState>()
        viewModel.state.observe(lifecycleOwner,Observer{
            list.add(it)
            if (list.size ==2){
                Assert.assertEquals(
                    list,
                    listOf(SplashMoviesUiState.Loading,SplashMoviesUiState.Success)
                )
            }
        })
        Assert.assertEquals(list.size,2)
    }
    @Test
    fun `failed login returns Loading then FailedUiState` (){
        val userId =1
        coEvery { loginUseCase.buildUseCase(User(userId)) } returns UseCaseResult.Failed()
        viewModel.performAction(SplashUiAction.Login(userId))
        val list= mutableListOf<SplashMoviesUiState>()
        viewModel.state.observe(lifecycleOwner,Observer{
            list.add(it)
            if (list.size ==2){
                Assert.assertEquals(
                    list,
                    listOf(SplashMoviesUiState.Loading,SplashMoviesUiState.Failed())
                )
            }
        })
        Assert.assertEquals(list.size,2)
    }
    @Test
    fun `success login doesnt return FailedUiState` (){
        val userId =1
        coEvery { loginUseCase.buildUseCase(User(userId)) } returns UseCaseResult.Success()
        viewModel.performAction(SplashUiAction.Login(userId))
        viewModel.state.observe(lifecycleOwner,Observer{
            Assert.assertFalse(it is SplashMoviesUiState.Failed)
        })
    }
    @Test
    fun `failed login doesnt return SuccessUiState` (){
        val userId =1
        coEvery { loginUseCase.buildUseCase(User(userId)) } returns UseCaseResult.Failed()
        viewModel.performAction(SplashUiAction.Login(userId))
        viewModel.state.observe(lifecycleOwner,Observer{
            Assert.assertFalse(it is SplashMoviesUiState.Success)
        })
    }
}