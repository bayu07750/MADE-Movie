package com.bayu07750.mademovie.core.data.network.service

import com.bayu07750.mademovie.core.data.local.mmkv.Session
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TheMovieDBClient(
    private val theMovieDBService: TheMovieDBService,
    private val session: Session,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun fetchTrendingMovie(
        timeWindow: String,
        page: Int,
    ) = withContext(ioDispatcher) {
        theMovieDBService.trendingMovie(
            timeWindow = timeWindow,
            page = page,
            language = getCurrentLang()
        )
    }

    suspend fun fetchPopularMovies(
        page: Int
    ) = withContext(ioDispatcher) { theMovieDBService.popularMovies(page = page, language = getCurrentLang()) }

    suspend fun fetchNowPlayingMovies(
        page: Int
    ) = withContext(ioDispatcher) { theMovieDBService.nowPlayingMovies(page = page, language = getCurrentLang()) }

    suspend fun fetchMovieDetail(
        movieId: Int
    ) = withContext(ioDispatcher) { theMovieDBService.movieDetail(movieId = movieId, language = getCurrentLang()) }

    suspend fun fetchMovieCredits(
        movieId: Int
    ) = withContext(ioDispatcher) { theMovieDBService.movieCredits(movieId = movieId, language = getCurrentLang()) }

    suspend fun fetchMovieDiscovery(
        page: Int,
        vararg genreIds: Int,
    ) = withContext(ioDispatcher) {
        theMovieDBService.movieDiscover(
            page = page,
            genres = genreIds.joinToString(separator = ","),
            language = getCurrentLang()
        )
    }

    suspend fun fetchSearchMovie(query: String, page: Int) = withContext(ioDispatcher) {
        theMovieDBService.searchMovie(page = page, query = query, language = getCurrentLang())
    }

    private fun getCurrentLang() = session.getString(Session.KEY_LANGUAGE)
}