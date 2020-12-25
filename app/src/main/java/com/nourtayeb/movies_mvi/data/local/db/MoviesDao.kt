package com.nourtayeb.movies_mvi.data.local.db

import androidx.room.*
import com.nourtayeb.movies_mvi.data.local.db.entities.MovieLocal
import com.nourtayeb.movies_mvi.data.local.db.entities.UserMovie


@Dao
interface MoviesDao {
    @Query("SELECT * FROM Movie")
    suspend fun getMovies() : List<MovieLocal>

    @Query("SELECT * FROM Movie where title LIKE :key")
    suspend fun search(key: String): List<MovieLocal>

    @Update
    suspend fun update(movie: MovieLocal):Int

//    @Query("UPDATE Movie SET isFav = :isFav WHERE id =:id")
//    suspend fun changeFavoriteForId(isFav: Boolean, id:Int):Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(list: List<MovieLocal>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(userMovie:UserMovie):Long

    @Query("DELETE FROM UserMovie WHERE userId = :userid and movieId = :movieId")
    fun removeFromFavorite(userid:Int,movieId:Int):Int


}