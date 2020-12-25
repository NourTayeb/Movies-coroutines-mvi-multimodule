package com.nourtayeb.movies_mvi.data.repository

import com.nourtayeb.movies_mvi.domain.entity.Movie

interface MoviesRepository {
    suspend fun searchMovies(key: String, fromRemote: Boolean,userId: Int): List<Movie>
    suspend fun toggleFavorite(isFav :Boolean, id:Int, userId:Int): Boolean
    suspend fun getFavorite(userId:Int): List<Movie>
}