package com.nourtayeb.movies_mvi.presentation.ui.splash

sealed class SplashUiAction{
    data class Login(val id:Int) : SplashUiAction()
}

sealed class SplashMoviesUiState {
    object Success: SplashMoviesUiState()
    object Loading : SplashMoviesUiState()
    data class Failed(val status: String? = null): SplashMoviesUiState()
    data class Exception(val exception: kotlin.Exception? = null): SplashMoviesUiState()
}