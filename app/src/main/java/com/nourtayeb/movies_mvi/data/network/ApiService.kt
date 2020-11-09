package com.nourtayeb.movies_mvi.data.network

import com.nourtayeb.movies_mvi.data.network.reponse.searchmovies.SearchMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") apiKey: String,@Query("query") key: String
    ): SearchMoviesResponse
}