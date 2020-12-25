package com.nourtayeb.ads.data.local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Ad")
data class AdLocal constructor(
    @field:PrimaryKey val id: Int,
    val name: String,
    val url: String
)