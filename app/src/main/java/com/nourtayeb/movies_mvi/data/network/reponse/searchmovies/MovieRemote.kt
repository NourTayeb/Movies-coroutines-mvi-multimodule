package com.nourtayeb.movies_mvi.data.network.reponse.searchmovies

data class MovieRemote (
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
){
    constructor(
         id: Int,
         poster_path: String?,
         release_date: String,
         vote_average: Double,
         title: String
    ):this(false,"", listOf(),id,"",title,"",0.0,poster_path ?: "",release_date,title,false,vote_average,0)
}