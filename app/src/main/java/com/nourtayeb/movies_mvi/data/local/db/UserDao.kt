package com.nourtayeb.movies_mvi.data.local.db

import androidx.room.*
import com.nourtayeb.movies_mvi.data.local.db.entities.UserLocal
import com.nourtayeb.movies_mvi.data.local.db.entities.UserMovies


@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    suspend fun getUsers() : List<UserLocal>

    @Query("SELECT * FROM User WHERE loggedIn = 1 ")
    suspend fun getLoggedInUser() : UserLocal

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: UserLocal):Long


    @Query("UPDATE User SET loggedIn = 0")
    suspend fun logOutAll():Int

    @Transaction
    @Query("SELECT * FROM User where id = :userid ")
    suspend fun getUserMovies(userid : Int): List<UserMovies>

}