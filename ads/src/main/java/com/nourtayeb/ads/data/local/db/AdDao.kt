package com.nourtayeb.ads.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nourtayeb.ads.data.local.db.entities.AdLocal


@Dao
interface AdDao {
    @Query("SELECT * FROM Ad where id = :id")
    suspend fun getRandomAd(id:Int): AdLocal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(list: List<AdLocal?>)
}