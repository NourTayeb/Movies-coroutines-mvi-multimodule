package com.nourtayeb.movies_mvi.data.local.db
import androidx.room.Database
import androidx.room.RoomDatabase
import com.nourtayeb.movies_mvi.data.local.db.entities.MovieLocal
import com.nourtayeb.movies_mvi.data.local.db.entities.UserLocal
import com.nourtayeb.movies_mvi.data.local.db.entities.UserMovie


@Database(entities = [MovieLocal::class,UserLocal::class,UserMovie::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun userDao(): UserDao
}