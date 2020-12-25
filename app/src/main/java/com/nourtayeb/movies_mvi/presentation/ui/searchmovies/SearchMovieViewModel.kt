package com.nourtayeb.movies_mvi.presentation.ui.searchmovies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.nourtayeb.movies_mvi.domain.UseCaseResult
import com.nourtayeb.movies_mvi.domain.usecase.AddToFavoriteUseCase
import com.nourtayeb.movies_mvi.domain.usecase.SearchMovieUseCase
import com.nourtayeb.movies_mvi.presentation.ui.splash.SplashMoviesUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class SearchMovieViewModel @ViewModelInject constructor(
    var dispatcher: CoroutineDispatcher,
    private val searchMovieUseCase: SearchMovieUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase
) : ViewModel() {


    private val action = MutableLiveData<SearchMoviesUiAction>()

    fun performAction(action: SearchMoviesUiAction) {
        this.action.value = action
    }


    val state: LiveData<SearchMoviesUiState> = action.switchMap {
        liveData(dispatcher) {
            emit(SearchMoviesUiState.Loading)
            try {
                val actionValue = action.value
                when (actionValue) {
                    is SearchMoviesUiAction.Search -> {
                        val result =
                            searchMovieUseCase.buildUseCase(actionValue.key, actionValue.fromRemote)
                        when (result) {
                            is UseCaseResult.Failed -> emit(SearchMoviesUiState.Failed("Search"))
                            is UseCaseResult.Success -> emit(SearchMoviesUiState.Searched(result.data))
                        }
                    }
                    is SearchMoviesUiAction.AddToFav -> {
                        val result =
                            addToFavoriteUseCase.buildUseCase(actionValue.isFav, actionValue.id)
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