package com.nourtayeb.movies_mvi.presentation.ui.searchmovies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.nourtayeb.movies_mvi.data.network.UseCaseResult
import com.nourtayeb.movies_mvi.domain.usecase.AddToFavoriteUseCase
import com.nourtayeb.movies_mvi.domain.usecase.SearchMovieUseCase
import com.nourtayeb.movies_mvi.presentation.ui.splash.SplashMoviesUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class SearchMovieViewModelDeprecated @ViewModelInject constructor(
    var dispatcher: CoroutineDispatcher,
    private val searchMovieUseCase: SearchMovieUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase
) : ViewModel() {

    private val _state:MutableLiveData<SearchMoviesUiState> = MutableLiveData()
    val state:LiveData<SearchMoviesUiState> get() = _state
    fun requestCoroutines(){
        viewModelScope.launch {
            _state.value = SearchMoviesUiState.Loading
            val result = searchMovieUseCase.buildUseCase("mile",true)
            if (result is UseCaseResult.Success) {
                _state.value = SearchMoviesUiState.Searched(result.data)
            }
        }
    }





    // ----------------------------------------- //

    val anotherState= liveData {
        emit(SearchMoviesUiState.Loading)
        val result = searchMovieUseCase.buildUseCase("mile",true)
        if (result is UseCaseResult.Success) {
            emit(SearchMoviesUiState.Searched(result.data))
        }
    }









}