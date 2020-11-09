package com.nourtayeb.movies_mvi.data.repository

import com.nourtayeb.movies_mvi.common.config.TMDB_API_KEY
import com.nourtayeb.movies_mvi.data.local.db.MoviesDao
import com.nourtayeb.movies_mvi.data.local.db.UserDao
import com.nourtayeb.movies_mvi.data.local.db.entities.UserMovie
import com.nourtayeb.movies_mvi.data.mapper.MovieMapper
import com.nourtayeb.movies_mvi.data.network.ApiService
import com.nourtayeb.movies_mvi.domain.entity.Movie
import com.nourtayeb.movies_mvi.domain.repository.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImp
@Inject constructor(private val apiService: ApiService,val mapper: MovieMapper,val moviesDao: MoviesDao,val userDao: UserDao):MoviesRepository {
    override suspend fun searchMovies(key: String,fromRemote: Boolean,userId:Int): List<Movie> {
        if(fromRemote){
            val apiResult = apiService.searchMovie(TMDB_API_KEY ,key).results
            moviesDao.insertMovies(apiResult.map { mapper.RetrofitToRoom(it) })
            val userMovies = userDao.getUserMovies(userId)
            val returnedDomain = apiResult.map { mapper.RetrofitToDomain(it) }
            if(!userMovies.isEmpty()){
                val favorite = userMovies[0].movies.map { it.id }.toSet()
                returnedDomain.forEach {
                    it.isFav = favorite.contains(it.id)
                }
            }
            return returnedDomain
        }else{
            return moviesDao.search("%"+key+"%").map { mapper.RoomToDomain(it) }
        }
    }

    override suspend fun toggleFavorite(isFav: Boolean, id: Int, userId:Int): Boolean {
        if(isFav){
            return moviesDao.addToFavorite(UserMovie(userId,id))!= 0L
        }else{
            return moviesDao.removeFromFavorite(userId,id) != 0
        }
    }
    override suspend fun getFavorite(userId:Int): List<Movie>{
        val favorite = userDao.getUserMovies(userId)
        if(favorite.isEmpty()){
            return listOf()
        }
        return favorite[0].movies.map { mapper.RoomToDomain(it) }.onEach { it.isFav = true }
    }
}