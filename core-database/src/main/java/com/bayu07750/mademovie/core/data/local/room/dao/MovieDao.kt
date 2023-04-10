package com.bayu07750.mademovie.core.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.bayu07750.mademovie.core.data.local.room.entity.GenreEntity
import com.bayu07750.mademovie.core.data.local.room.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Upsert
    suspend fun insertMovie(value: MovieEntity)

    @Delete
    suspend fun deleteMovie(value: MovieEntity)

    @Query("SELECT * FROM bookmarked_movie_table WHERE id = :id")
    fun getBookmarkedMovie(id: Int): Flow<MovieEntity?>

    @Query("SELECT * FROM bookmarked_movie_table")
    fun getAllBookmarkedMovie(): Flow<List<MovieEntity>>

    @Upsert
    suspend fun insertGenres(values: List<GenreEntity>)

    @Query("SELECT * FROM list_genre_table")
    fun getAllMovieGenre(): Flow<List<GenreEntity>>
}