package com.nourtayeb.movies_mvi.presentation.ui.searchmovies

import com.nourtayeb.movies_mvi.domain.entity.Movie

sealed class SearchMoviesUiAction{

    data class Search(val key:String,val fromRemote: Boolean) : SearchMoviesUiAction()
    data class AddToFav(val isFav: Boolean,val  id: Int) : SearchMoviesUiAction()
}

sealed class SearchMoviesUiState {
    data class Searched(val data: List<Movie>? = null): SearchMoviesUiState()
    object AddedToFavorite: SearchMoviesUiState()
    object Loading : SearchMoviesUiState()
    data class Failed(val status: String? = null): SearchMoviesUiState()
    data class Exception(val exception: kotlin.Exception? = null): SearchMoviesUiState()
}