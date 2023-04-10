package com.bayu07750.mademovie.core.domain.repository

import androidx.paging.PagingData
import com.bayu07750.mademovie.core.data.Resource
import com.bayu07750.mademovie.core.domain.model.Cast
import com.bayu07750.mademovie.core.domain.model.Genre
import com.bayu07750.mademovie.core.domain.model.Movie
import com.bayu07750.mademovie.core.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun fetchTrendingMovies(timeWindow: String): Flow<PagingData<Movie>>
    fun fetchTrendingMovies(timeWindow: String, page: Int): Flow<Resource<List<Movie>>>
    fun fetchPopularMovies(): Flow<PagingData<Movie>>
    fun fetchPopularMovies(page: Int): Flow<Resource<List<Movie>>>
    fun fetchNowPlayingMovies(): Flow<PagingData<Movie>>
    fun fetchNowPlayingMovies(page: Int): Flow<Resource<List<Movie>>>
    fun fetchMovieDetail(movieId: Int): Flow<Resource<MovieDetail>>
    fun fetchMovieCasts(movieId: Int): Flow<Resource<List<Cast>>>
    suspend fun bookmarkMovie(movie: Movie, remove: Boolean)
    fun getBookmarkedMovieById(movieId: Int): Flow<Movie?>
    fun getAllBookmarkedMovie(): Flow<List<Movie>>
    fun getAllMovieGenre(): Flow<List<Genre>>
    fun fetchMovieDiscovery(vararg genreIds: Int): Flow<PagingData<Movie>>
    fun fetchSearchMovie(query: String): Flow<PagingData<Movie>>
}