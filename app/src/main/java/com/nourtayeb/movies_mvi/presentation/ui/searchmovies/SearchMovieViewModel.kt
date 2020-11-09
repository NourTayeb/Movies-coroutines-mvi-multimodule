package com.nourtayeb.movies_mvi.presentation.ui.searchmovies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.nourtayeb.movies_mvi.data.network.UseCaseResult
import com.nourtayeb.movies_mvi.domain.usecase.AddToFavoriteUseCase
import com.nourtayeb.movies_mvi.domain.usecase.SearchMovieUseCase
import com.nourtayeb.movies_mvi.presentation.ui.splash.SplashMoviesUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class SearchMovieViewModel @ViewModelInject constructor(
    var dispatcher: CoroutineDispatcher,
    private val searchMovieUseCase: SearchMovieUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase
) : ViewModel() {


    fun performAction(action: SearchMoviesUiAction): LiveData<SearchMoviesUiState> {
        return liveData(dispatcher) {
            emit(SearchMoviesUiState.Loading)
            try {
                when (action) {
                    is SearchMoviesUiAction.Search -> {
                        val result = searchMovieUseCase.buildUseCase(action.key, action.fromRemote)
                        when (result) {
                            is UseCaseResult.Failed -> emit(SearchMoviesUiState.Failed("Search"))
                            is UseCaseResult.Success -> emit(SearchMoviesUiState.Searched(result.data))
                        }
                    }
                    is SearchMoviesUiAction.AddToFav -> {
                        val result = addToFavoriteUseCase.buildUseCase(action.isFav, action.id)
                        when (result) {
                            is UseCaseResult.Failed -> emit(SearchMoviesUiState.Failed("Fav"))
                            is UseCaseResult.Success -> emit(SearchMoviesUiState.AddedToFavorite)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(SearchMoviesUiState.Exception(e))
            }
        }
    }





}