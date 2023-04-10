package com.bayu07750.mademovie.core.domain.usecase

import com.bayu07750.mademovie.core.domain.repository.MovieRepository

class GetPopularMoviesPagingUseCase(private val movieRepository: MovieRepository) {

    operator fun invoke() = movieRepository.fetchPopularMovies()
}