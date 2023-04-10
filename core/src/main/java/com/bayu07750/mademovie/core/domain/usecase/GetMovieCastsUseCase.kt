package com.bayu07750.mademovie.core.domain.usecase

import com.bayu07750.mademovie.core.domain.repository.MovieRepository

class GetMovieCastsUseCase(private val movieRepository: MovieRepository) {

    operator fun invoke(movieId: Int) = movieRepository.fetchMovieCasts(movieId)
}