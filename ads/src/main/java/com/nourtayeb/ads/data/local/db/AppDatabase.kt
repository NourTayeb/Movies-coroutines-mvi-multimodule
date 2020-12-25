package com.nourtayeb.ads.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nourtayeb.ads.data.local.db.entities.AdLocal


@Database(entities = [AdLocal::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun adDao(): AdDao
}