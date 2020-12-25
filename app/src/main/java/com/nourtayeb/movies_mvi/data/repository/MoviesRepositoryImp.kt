package com.nourtayeb.movies_mvi.data.repository

import com.nourtayeb.movies_mvi.common.config.TMDB_API_KEY
import com.nourtayeb.movies_mvi.data.local.db.AppDatabase
import com.nourtayeb.movies_mvi.data.local.db.MoviesDao
import com.nourtayeb.movies_mvi.data.local.db.UserDao
import com.nourtayeb.movies_mvi.data.local.db.entities.UserMovie
import com.nourtayeb.movies_mvi.data.mapper.MovieMapper
import com.nourtayeb.movies_mvi.data.network.ApiService
import com.nourtayeb.movies_mvi.domain.entity.Movie
import kotlinx.coroutines.*
import javax.inject.Inject

class MoviesRepositoryImp
@Inject constructor(private val apiService: ApiService,val mapper: MovieMapper,val moviesDao: MoviesDao,val userDao: UserDao):
    MoviesRepository {

    @Inject
    lateinit var AppDatabase: AppDatabase
   
    override suspend fun searchMovies(key: String,fromRemote: Boolean,userId:Int): List<Movie> = coroutineScope  {
        if(fromRemote){
            val apiResultDeferred = async { apiService.searchMovie(TMDB_API_KEY ,key) }//.results
            val userMoviesDeferred =  async { userDao.getUserMovies(userId) }
            val apiResult = apiResultDeferred.await().results
            val userMovies = userMoviesDeferred.await()
            moviesDao.insertMovies(apiResult.map { mapper.RetrofitToRoom(it) })
            val returnedDomain = apiResult.map { mapper.RetrofitToDomain(it) }
            if(!userMovies.isEmpty()){
                val favorite = userMovies[0].movies.map { it.id }.toSet()
                returnedDomain.forEach {
                    it.isFav = favorite.contains(it.id)
                }
            }
             returnedDomain
        }else{
             moviesDao.search("%"+key+"%").map { mapper.RoomToDomain(it) }
        }
    }

    override suspend fun toggleFavorite(isFav: Boolean, id: Int, userId:Int): Boolean {
        delay(1000)
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