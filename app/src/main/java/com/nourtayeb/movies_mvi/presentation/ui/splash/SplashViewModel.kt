package com.nourtayeb.movies_mvi.presentation.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.nourtayeb.movies_mvi.data.network.UseCaseResult
import com.nourtayeb.movies_mvi.domain.entity.User
import com.nourtayeb.movies_mvi.domain.usecase.LoginUseCase
import kotlinx.coroutines.CoroutineDispatcher
import java.lang.Exception

class SplashViewModel @ViewModelInject constructor(
    val dispatcher: CoroutineDispatcher,
    val loginUseCase: LoginUseCase):ViewModel(){
    fun performAction(action:SplashUiAction) :LiveData<SplashMoviesUiState>{
        return  liveData(dispatcher) {
            emit(SplashMoviesUiState.Loading)
            if(action is SplashUiAction.Login) {
                try {
                    if(loginUseCase.buildUseCase(User(action.id)) is UseCaseResult.Success){
                        emit(SplashMoviesUiState.Success)
                    }else{
                        emit(SplashMoviesUiState.Failed())
                    }
                }catch (e:Exception){
                    emit(SplashMoviesUiState.Exception(e))
                }
            }
        }

    }
}