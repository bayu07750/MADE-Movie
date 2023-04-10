package com.bayu07750.mademovie.core.domain.usecase

import com.bayu07750.mademovie.core.domain.repository.MovieRepository

class GetTrendingMoviesUseCase(private val movieRepository: MovieRepository) {

    operator fun invoke(timeWindow: String, page: Int) = movieRepository.fetchTrendingMovies(timeWindow, page)
}