package com.bayu07750.mademovie.core.domain.usecase

import com.bayu07750.mademovie.core.domain.repository.MovieRepository

class GetMovieDiscoveryUseCase(private val movieRepository: MovieRepository) {

    operator fun invoke(vararg genreIds: Int) = movieRepository.fetchMovieDiscovery(*genreIds)
}