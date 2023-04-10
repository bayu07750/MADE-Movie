package com.bayu07750.mademovie.utils

import androidx.paging.PagingData
import com.bayu07750.mademovie.core.data.Resource
import com.bayu07750.mademovie.core.domain.model.Cast
import com.bayu07750.mademovie.core.domain.model.Genre
import com.bayu07750.mademovie.core.domain.model.Movie
import com.bayu07750.mademovie.core.domain.model.MovieDetail
import com.bayu07750.mademovie.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestMovieRepository: MovieRepository {

    override fun fetchTrendingMovies(timeWindow: String): Flow<PagingData<Movie>> = flow { emit(PagingData.from(DataDummy.movies)) }

    override fun fetchTrendingMovies(timeWindow: String, page: Int): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Success(DataDummy.movies))
    }

    override fun fetchPopularMovies(): Flow<PagingData<Movie>> = flow { emit(PagingData.from(DataDummy.movies)) }

    override fun fetchPopularMovies(page: Int): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Success(DataDummy.movies))
    }

    override fun fetchNowPlayingMovies(): Flow<PagingData<Movie>> = flow { emit(PagingData.from(DataDummy.movies)) }

    override fun fetchNowPlayingMovies(page: Int): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Success(DataDummy.movies))
    }

    override fun fetchMovieDetail(movieId: Int): Flow<Resource<MovieDetail>> = flow {
        emit(Resource.Success(DataDummy.movieDetail))
    }

    override fun fetchMovieCasts(movieId: Int): Flow<Resource<List<Cast>>> = flow {
        emit(Resource.Success(DataDummy.casts))
    }

    override suspend fun bookmarkMovie(movie: Movie, remove: Boolean) {

    }

    override fun getBookmarkedMovieById(movieId: Int): Flow<Movie?> = flow { emit(DataDummy.movie) }

    override fun getAllBookmarkedMovie(): Flow<List<Movie>> = flow { emit(DataDummy.movies) }

    override fun getAllMovieGenre(): Flow<List<Genre>> = flow { emit(DataDummy.genres) }

    override fun fetchMovieDiscovery(vararg genreIds: Int): Flow<PagingData<Movie>> = flow { emit(PagingData.from(DataDummy.movies)) }

    override fun fetchSearchMovie(query: String): Flow<PagingData<Movie>> = flow { emit(PagingData.from(DataDummy.movies)) }
}