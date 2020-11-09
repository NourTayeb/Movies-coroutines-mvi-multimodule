package com.nourtayeb.movies_mvi.data.network.reponse.searchmovies

data class SearchMoviesResponse(
    val page: Int,
    val results: List<MovieRemote>,
    val total_pages: Int,
    val total_results: Int
)
{
    constructor(results: List<MovieRemote>):this(0,results,0,0)
}