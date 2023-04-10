package com.bayu07750.mademovie.core.data.network.service

import com.bayu07750.mademovie.core.data.network.BuildConfig
import com.bayu07750.mademovie.core.data.network.model.MovieCreditsResponse
import com.bayu07750.mademovie.core.data.network.model.MovieDetailResponse
import com.bayu07750.mademovie.core.data.network.model.MoviesResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBService {

    @GET("trending/movie/{timeWindow}")
    suspend fun trendingMovie(
        @Path("timeWindow") timeWindow: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
    ): ApiResponse<MoviesResponse>

    @GET("movie/popular")
    suspend fun popularMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
    ): ApiResponse<MoviesResponse>

    @GET("movie/now_playing")
    suspend fun nowPlayingMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
    ): ApiResponse<MoviesResponse>

    @GET("movie/{movieId}/credits")
    suspend fun movieCredits(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US",
    ): ApiResponse<MovieCreditsResponse>

    @GET("movie/{movieId}")
    suspend fun movieDetail(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US",
    ): ApiResponse<MovieDetailResponse>

    @GET("discover/movie")
    suspend fun movieDiscover(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("with_genres") genres: String,
        @Query("page") page: Int,
    ): ApiResponse<MoviesResponse>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("page") page: Int,
        @Query("query") query: String,
    ): ApiResponse<MoviesResponse>
}