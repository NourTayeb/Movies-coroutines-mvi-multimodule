package com.nourtayeb.movies_mvi.data.local.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    primaryKeys = [ "userId","movieId"]
)
data class UserMovie(
    val userId: Int,
    val movieId: Int
)

data class UserMovies (
    @Embedded
    val userLocal: UserLocal,
    @Relation(
        parentColumn = "id",
        entity = MovieLocal::class,
        entityColumn = "id",
        associateBy = Junction(
            value = UserMovie::class,
            parentColumn = "userId",
            entityColumn = "movieId"
        )
    )
    val movies: List<MovieLocal>
)