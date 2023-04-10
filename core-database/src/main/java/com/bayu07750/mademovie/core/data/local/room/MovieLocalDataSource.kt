package com.bayu07750.mademovie.core.data.local.room

import com.bayu07750.mademovie.core.data.local.room.dao.MovieDao
import com.bayu07750.mademovie.core.data.local.room.entity.MovieEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class MovieLocalDataSource(
    private val movieDao: MovieDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun insertMovie(value: MovieEntity) = withContext(ioDispatcher) { movieDao.insertMovie(value) }

    suspend fun deleteMovie(value: MovieEntity) = withContext(ioDispatcher) { movieDao.deleteMovie(value) }

    fun getBookmarkedMovie(movieId: Int) =
        movieDao.getBookmarkedMovie(movieId).catch { emit(null) }.flowOn(ioDispatcher)

    fun getAllBookmarkedMovie() = movieDao.getAllBookmarkedMovie().catch { emit(emptyList()) }.flowOn(ioDispatcher)

    fun getAllMovieGenre() = movieDao.getAllMovieGenre().catch { emit(emptyList()) }.flowOn(ioDispatcher)
}