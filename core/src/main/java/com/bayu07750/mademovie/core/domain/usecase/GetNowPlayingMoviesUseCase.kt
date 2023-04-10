package com.bayu07750.mademovie.core.domain.usecase

import com.bayu07750.mademovie.core.domain.repository.MovieRepository

class GetNowPlayingMoviesUseCase(private val movieRepository: MovieRepository) {

    operator fun invoke(page: Int) = movieRepository.fetchNowPlayingMovies(page)
}