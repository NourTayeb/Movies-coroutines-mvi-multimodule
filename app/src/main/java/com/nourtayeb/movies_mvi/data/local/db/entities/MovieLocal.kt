package com.nourtayeb.movies_mvi.data.local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Movie")
data class MovieLocal constructor(
    @field:PrimaryKey val id: Int,
    val poster_path: String?,
    val release_date: String,
    val vote_average: Double,
    val title: String
)