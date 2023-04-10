package com.bayu07750.mademovie.core.domain.usecase

import androidx.paging.PagingData
import com.bayu07750.mademovie.core.domain.model.Movie
import com.bayu07750.mademovie.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetSearchMovieUseCase(private val movieRepository: MovieRepository) {

    operator fun invoke(query: String): Flow<PagingData<Movie>> = movieRepository.fetchSearchMovie(query)
}