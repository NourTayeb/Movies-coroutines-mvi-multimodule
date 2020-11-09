package com.nourtayeb.movies_mvi.common

import com.nourtayeb.movies_mvi.data.local.db.entities.UserLocal
import com.nourtayeb.movies_mvi.data.local.db.entities.UserMovies
import com.nourtayeb.movies_mvi.data.mapper.MovieMapper
import com.nourtayeb.movies_mvi.data.network.reponse.searchmovies.SearchMoviesResponse
import com.nourtayeb.movies_mvi.domain.entity.Movie
import com.nourtayeb.movies_mvi.domain.entity.User

val dummyMovies = listOf(
    Movie(1,"/url","2020-10-10",10.0,"8 mile"),
    Movie(2,"/url","2020-10-10",10.0,"8 mile"),
    Movie(3,"/url","2020-10-10",10.0,"8 mile"),
    Movie(4,"/url","2020-10-10",10.0,"8 mile")
)
val emptyMovies = emptyList<Movie>()
val emptyUserMovies = emptyList<UserMovies>()
fun dummyMoviesReponse(mapper: MovieMapper) =
    SearchMoviesResponse(dummyMovies.map { mapper.DomainToRetrofit(it) })


fun dummyUserMovies(movies:List<Movie>, mapper: MovieMapper) :List<UserMovies>{
    return listOf(
        UserMovies(UserLocal(2,true),
            movies.map { mapper.DomainToRoom(it) }
        )
    )
}

fun dummyMoviesRoom(mapper: MovieMapper) =
    dummyMovies.map { mapper.DomainToRoom(it) }

val dummyUser = User(1)