package com.nourtayeb.movies_mvi.domain.entity

import com.nourtayeb.movies_mvi.common.config.IMAGES_BASE_URL

data class Movie (
    val id: Int,
    val poster_path: String?,
    val release_date: String,
    val vote_average: Double,
    val title: String,
    var isFav: Boolean = false
) {
    fun getImage(): String {
        poster_path?.let{ return IMAGES_BASE_URL + poster_path }
        return ""
    }

    constructor(poster_path: String?):this(0,poster_path,"",0.0,"",false)
}