package com.bayu07750.mademovie.core.domain.usecase

import com.bayu07750.mademovie.core.domain.repository.MovieRepository

class GetTrendingMoviesPagingUseCase(private val movieRepository: MovieRepository) {

    operator fun invoke(timeWindow: String) = movieRepository.fetchTrendingMovies(timeWindow)
}