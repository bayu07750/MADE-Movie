package com.bayu07750.mademovie.core.domain.usecase

import com.bayu07750.mademovie.core.domain.repository.MovieRepository

class GetMovieDetailUseCase(private val movieRepository: MovieRepository) {

    operator fun invoke(movieId: Int) = movieRepository.fetchMovieDetail(movieId)
}