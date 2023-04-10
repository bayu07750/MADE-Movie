package com.bayu07750.mademovie.core.domain.usecase

import com.bayu07750.mademovie.core.domain.model.Movie
import com.bayu07750.mademovie.core.domain.repository.MovieRepository

class AddBookmarkedMovieUseCase(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(movie: Movie) = movieRepository.bookmarkMovie(movie, false)
}