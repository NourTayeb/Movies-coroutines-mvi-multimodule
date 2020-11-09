package com.nourtayeb.movies_mvi.common

import com.nourtayeb.movies_mvi.domain.entity.Movie
import com.nourtayeb.movies_mvi.domain.entity.User

val dumUser = User(12)
val dumMovie = Movie(1,"/url","2020-10-10",10.0,"8 mile")

val movies1999 = listOf(
    Movie(2,"/url","1999-01-01",3.0,"1999 movie 1"),
    Movie(2,"/url","1999-01-01",3.0,"1999 movie 2")
)