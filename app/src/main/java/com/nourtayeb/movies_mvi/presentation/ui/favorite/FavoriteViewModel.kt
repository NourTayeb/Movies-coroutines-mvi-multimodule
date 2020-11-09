package com.nourtayeb.movies_mvi.presentation.ui.favorite

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.nourtayeb.movies_mvi.data.network.UseCaseResult
import com.nourtayeb.movies_mvi.domain.usecase.AddToFavoriteUseCase
import com.nourtayeb.movies_mvi.domain.usecase.GetFavoriteUseCase
import kotlinx.coroutines.CoroutineDispatcher

class FavoriteViewModel @ViewModelInject constructor(
    val dispatcher: CoroutineDispatcher,
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase
): ViewModel() {



    fun performAction(action:FavoriteMoviesUiAction):LiveData<FavoriteMoviesUiState>{
        return liveData (dispatcher) {
            emit(FavoriteMoviesUiState.Loading)
            try {
                when(action) {
                    is FavoriteMoviesUiAction.LoadFavorite   -> {
                        val result = getFavoriteUseCase.buildUseCase()
                        when (result) {
                            is  UseCaseResult.Failed -> emit(FavoriteMoviesUiState.Failed(""))
                            is  UseCaseResult.Success -> emit(FavoriteMoviesUiState.FavoriteLoaded(result.data))
                        }
                    }
                    is FavoriteMoviesUiAction.AddToFav -> {
                        val result = addToFavoriteUseCase.buildUseCase(action.isFav,action.id)
                        when(result){
                            is  UseCaseResult.Failed -> emit(FavoriteMoviesUiState.Failed(""))
                            is  UseCaseResult.Success -> emit(FavoriteMoviesUiState.AddedToFavorite(action.id,action.isFav))
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(FavoriteMoviesUiState.Loading)
            }
        }
    }



}