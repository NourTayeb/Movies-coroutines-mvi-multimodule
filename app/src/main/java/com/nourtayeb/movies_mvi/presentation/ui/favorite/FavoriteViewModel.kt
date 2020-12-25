package com.nourtayeb.movies_mvi.presentation.ui.favorite

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.nourtayeb.ads.domain.usecase.ShowImageAdsUseCase
import com.nourtayeb.movies_mvi.domain.UseCaseResult
import com.nourtayeb.movies_mvi.domain.usecase.AddToFavoriteUseCase
import com.nourtayeb.movies_mvi.domain.usecase.GetFavoriteUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map

class FavoriteViewModel @ViewModelInject constructor(
    val dispatcher: CoroutineDispatcher,
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val showImageAdsUseCase: ShowImageAdsUseCase
): ViewModel() {


    private val action= MutableLiveData<FavoriteMoviesUiAction>()
    init {
        performAction(FavoriteMoviesUiAction.LoadFavorite)
    }
    fun performAction(action: FavoriteMoviesUiAction){
        this.action.value=action
    }
    val state:LiveData<FavoriteMoviesUiState> = action.switchMap {
         liveData (dispatcher) {
            emit(FavoriteMoviesUiState.Loading)
            try {
                val actionValue = action.value
                when(actionValue) {
                    is FavoriteMoviesUiAction.LoadFavorite   -> {
                        val result = getFavoriteUseCase.buildUseCase()
                        when (result) {
                            is  UseCaseResult.Failed -> emit(FavoriteMoviesUiState.Failed(""))
                            is  UseCaseResult.Success -> emit(FavoriteMoviesUiState.FavoriteLoaded(result.data))
                        }
                    }
                    is FavoriteMoviesUiAction.ShowAds   -> {
                        emitSource(showImageAdsUseCase.buildUseCase(6).map { FavoriteMoviesUiState.AdLoaded(it) }.asLiveData(dispatcher))
                    }
                    is FavoriteMoviesUiAction.AddToFav -> {
                        val result = addToFavoriteUseCase.buildUseCase(actionValue.isFav,actionValue.id)
                        when(result){
                            is  UseCaseResult.Failed -> emit(FavoriteMoviesUiState.Failed(""))
                            is  UseCaseResult.Success -> emit(FavoriteMoviesUiState.AddedToFavorite(actionValue.id,actionValue.isFav))
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(FavoriteMoviesUiState.Exception(e))
            }
        }
    }



}