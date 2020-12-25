package com.nourtayeb.movies_mvi.presentation.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.nourtayeb.movies_mvi.domain.UseCaseResult
import com.nourtayeb.movies_mvi.domain.entity.User
import com.nourtayeb.movies_mvi.domain.usecase.LoginUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import java.lang.Exception

class SplashViewModel @ViewModelInject constructor(
    val dispatcher: CoroutineDispatcher,
    val loginUseCase: LoginUseCase
) : ViewModel() {


    private val action = MutableLiveData<SplashUiAction>()

    fun performAction(action: SplashUiAction) {
        this.action.value = action
    }

    val state: LiveData<SplashMoviesUiState> = action.switchMap {
        liveData(dispatcher) {
            emit(SplashMoviesUiState.Loading)
            val actionValue = action.value
            if (actionValue is SplashUiAction.Login) {
                try {
                    if (loginUseCase.buildUseCase(User(actionValue.id)) is UseCaseResult.Success) {
                        emit(SplashMoviesUiState.Success)
                    } else {
                        emit(SplashMoviesUiState.Failed())
                    }
                } catch (e: Exception) {
                    emit(SplashMoviesUiState.Exception(e))
                }
            }
        }
    }

}