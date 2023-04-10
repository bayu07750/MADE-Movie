package com.bayu07750.mademovie.core.domain.usecase

import com.bayu07750.mademovie.core.data.Resource
import com.bayu07750.mademovie.core.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull

class GetMovieDetailAndCastUseCase(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieCastsUseCase: GetMovieCastsUseCase,
    private val getBookmarkedMovieByIdUseCase: GetBookmarkedMovieByIdUseCase,
) {

    operator fun invoke(movieId: Int): Flow<Resource<MovieDetail>> =
        combine(getMovieDetailUseCase(movieId), getMovieCastsUseCase(movieId)) { movieDetailResource, castsResource ->
            when {
                movieDetailResource is Resource.Loading || castsResource is Resource.Loading -> Resource.Loading()
                movieDetailResource is Resource.Error || castsResource is Resource.Error -> Resource.Error(
                    movieDetailResource.message ?: castsResource.message ?: ""
                )
                movieDetailResource is Resource.Success && castsResource is Resource.Success -> {
                    val movieDetail = movieDetailResource.data?.copy(
                        casts = castsResource.data ?: emptyList(),
                        bookmarked = getBookmarkedMovieByIdUseCase(movieId).firstOrNull() != null
                    )!!
                    Resource.Success(movieDetail)
                }
                else -> Resource.Error(movieDetailResource.message ?: castsResource.message ?: "null")
            }
        }
}