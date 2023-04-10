package com.bayu07750.mademovie.core.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_movie_table")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("posterRaw")
    val posterRaw: String,
)
