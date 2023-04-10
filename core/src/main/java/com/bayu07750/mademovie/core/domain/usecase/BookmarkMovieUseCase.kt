package com.bayu07750.mademovie.core.domain.usecase

import com.bayu07750.mademovie.core.domain.model.Movie

class BookmarkMovieUseCase(
    private val addBookmarkedMovieUseCase: AddBookmarkedMovieUseCase,
    private val deleteBookmarkedMovieUseCase: DeleteBookmarkedMovieUseCase,
) {

    suspend operator fun invoke(movie: Movie) {
        if (movie.bookmarked) {
            deleteBookmarkedMovieUseCase(movie)
            return
        }

        addBookmarkedMovieUseCase(movie)
    }
}