package com.nourtayeb.movies_mvi.data.mapper

import com.nourtayeb.movies_mvi.common.base.BaseMapper
import com.nourtayeb.movies_mvi.data.local.db.entities.MovieLocal
import com.nourtayeb.movies_mvi.data.network.reponse.searchmovies.MovieRemote
import com.nourtayeb.movies_mvi.domain.entity.Movie

class MovieMapper :BaseMapper<Movie,MovieLocal,MovieRemote>{
    override fun RoomToDomain(data: MovieLocal) =
        Movie(data.id,data.poster_path,data.release_date,data.vote_average,data.title)

    override fun RetrofitToDomain(data: MovieRemote) =
         Movie(data.id,data.poster_path,data.release_date,data.vote_average,data.title)

    override fun DomainToRoom(domain: Movie)=
        MovieLocal(domain.id,domain.poster_path,domain.release_date,domain.vote_average,domain.title)

    override fun RetrofitToRoom(data: MovieRemote) =
        MovieLocal(data.id,data.poster_path,data.release_date,data.vote_average,data.title)

    override fun DomainToRetrofit(domain: Movie)=
        MovieRemote(domain.id,domain.poster_path,domain.release_date,domain.vote_average,domain.title)


}