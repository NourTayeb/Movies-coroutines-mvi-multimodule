package com.nourtayeb.movies_mvi.data.local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "User")
data class UserLocal constructor(
    @field:PrimaryKey val id: Int,
    var loggedIn:Boolean = false
)