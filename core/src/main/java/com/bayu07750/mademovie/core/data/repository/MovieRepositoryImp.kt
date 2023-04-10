package com.bayu07750.mademovie.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.bayu07750.mademovie.core.data.local.room.MovieLocalDataSource
import com.bayu07750.mademovie.core.data.network.model.MoviesResponse
import com.bayu07750.mademovie.core.data.network.service.TheMovieDBClient
import com.bayu07750.mademovie.core.data.Resource
import com.bayu07750.mademovie.core.data.source.local.room.entity.mapper.GenreEntityMapper
import com.bayu07750.mademovie.core.data.source.local.room.entity.mapper.MovieEntityMapper
import com.bayu07750.mademovie.core.data.source.paging.MoviePagingSource
import com.bayu07750.mademovie.core.data.source.network.model.mapper.CastResponseMapper
import com.bayu07750.mademovie.core.data.source.network.model.mapper.MovieDetailMapper
import com.bayu07750.mademovie.core.data.source.network.model.mapper.MovieResponseMapper
import com.bayu07750.mademovie.core.domain.model.Cast
import com.bayu07750.mademovie.core.domain.model.Genre
import com.bayu07750.mademovie.core.domain.model.Movie
import com.bayu07750.mademovie.core.domain.model.MovieDetail
import com.bayu07750.mademovie.core.domain.repository.MovieRepository
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.messageOrNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class MovieRepositoryImp(
    private val theMovieDBClient: TheMovieDBClient,
    private val movieLocalDataSource: MovieLocalDataSource
) : MovieRepository {

    override fun fetchTrendingMovies(timeWindow: String) = fetchMoviesPaging {
        theMovieDBClient.fetchTrendingMovie(timeWindow, it)
    }

    override fun fetchTrendingMovies(timeWindow: String, page: Int) = fetchMovies {
        theMovieDBClient.fetchTrendingMovie(timeWindow, page)
    }

    override fun fetchPopularMovies() = fetchMoviesPaging {
        theMovieDBClient.fetchPopularMovies(it)
    }

    override fun fetchPopularMovies(page: Int) = fetchMovies {
        theMovieDBClient.fetchPopularMovies(page)
    }

    override fun fetchNowPlayingMovies() = fetchMoviesPaging {
        theMovieDBClient.fetchNowPlayingMovies(it)
    }

    override fun fetchNowPlayingMovies(page: Int) = fetchMovies {
        theMovieDBClient.fetchNowPlayingMovies(page)
    }

    override fun fetchMovieDetail(movieId: Int): Flow<Resource<MovieDetail>> = flow {
        emit(Resource.Loading())

        when (val response = theMovieDBClient.fetchMovieDetail(movieId = movieId)) {
            is ApiResponse.Failure.Error -> emit(Resource.Error(message = response.messageOrNull.orEmpty()))
            is ApiResponse.Failure.Exception -> emit(Resource.Error(response.exception.message.orEmpty()))
            is ApiResponse.Success -> emit(Resource.Success(data = MovieDetailMapper(response.data)))
        }
    }

    override fun fetchMovieCasts(movieId: Int): Flow<Resource<List<Cast>>> = flow {
        emit(Resource.Loading())

        when (val response = theMovieDBClient.fetchMovieCredits(movieId)) {
            is ApiResponse.Failure.Error -> emit(Resource.Error(message = response.messageOrNull.orEmpty()))
            is ApiResponse.Failure.Exception -> emit(Resource.Error(response.exception.message.orEmpty()))
            is ApiResponse.Success -> {
                val casts = response.data.castResponse?.map { CastResponseMapper(it) } ?: emptyList()
                emit(Resource.Success(data = casts))
            }
        }
    }

    override suspend fun bookmarkMovie(movie: Movie, remove: Boolean) {
        val entity = MovieEntityMapper.asEntityFromDomain(movie)
        if (remove) {
            movieLocalDataSource.deleteMovie(entity)
        } else {
            movieLocalDataSource.insertMovie(entity)
        }
    }

    override fun getBookmarkedMovieById(movieId: Int): Flow<Movie?> {
        return movieLocalDataSource.getBookmarkedMovie(movieId).map {
            it ?: return@map null
            MovieEntityMapper.asDomain(it)
        }
    }

    override fun getAllBookmarkedMovie(): Flow<List<Movie>> {
        return movieLocalDataSource.getAllBookmarkedMovie().map {
            it.map { entity -> MovieEntityMapper.asDomain(entity) }
        }.onEach {
            Timber.d("${it.size} + $it")
        }
    }

    override fun getAllMovieGenre(): Flow<List<Genre>> = movieLocalDataSource.getAllMovieGenre()
        .map { entities -> entities.map { entity -> GenreEntityMapper.asDomain(entity) } }

    override fun fetchMovieDiscovery(vararg genreIds: Int) = fetchMoviesPaging {
        theMovieDBClient.fetchMovieDiscovery(it, *genreIds)
    }

    override fun fetchSearchMovie(query: String) = fetchMoviesPaging {
        theMovieDBClient.fetchSearchMovie(query, it)
    }

    private fun fetchMoviesPaging(
        source: suspend (page: Int) -> ApiResponse<MoviesResponse>
    ): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            initialLoadSize = 20,
        ),
        pagingSourceFactory = {
            MoviePagingSource(source)
        }
    ).flow.map {
        it.map { movieResponse ->
            MovieResponseMapper(movieResponse)
        }
    }

    private inline fun fetchMovies(
        crossinline source: suspend () -> ApiResponse<MoviesResponse>
    ) = flow {
        emit(Resource.Loading())

        when (val response = source.invoke()) {
            is ApiResponse.Failure.Error -> emit(Resource.Error(message = response.messageOrNull.orEmpty()))
            is ApiResponse.Failure.Exception -> emit(Resource.Error(response.exception.message.orEmpty()))
            is ApiResponse.Success -> emit(Resource.Success(data = response.data.results?.map { MovieResponseMapper(it) }
                ?: emptyList()))
        }
    }
}