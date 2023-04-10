package com.bayu07750.mademovie.core.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_genre_table")
data class GenreEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("posterRaw")
    val posterRaw: String
)