package com.nourtayeb.movies_mvi.presentation.ui.favorite

import com.nourtayeb.movies_mvi.domain.entity.Movie

sealed class FavoriteMoviesUiAction{
    object LoadFavorite : FavoriteMoviesUiAction()
    data class AddToFav(val isFav: Boolean,val  id: Int) : FavoriteMoviesUiAction()
}

sealed class FavoriteMoviesUiState {
    data class FavoriteLoaded(val data: List<Movie>? = null): FavoriteMoviesUiState()
    data class AddedToFavorite(val id:Int,val isFav: Boolean): FavoriteMoviesUiState()
    object Loading : FavoriteMoviesUiState()
    data class Failed(val status: String? = null): FavoriteMoviesUiState()
    data class Exception(val exception: kotlin.Exception? = null): FavoriteMoviesUiState()
}