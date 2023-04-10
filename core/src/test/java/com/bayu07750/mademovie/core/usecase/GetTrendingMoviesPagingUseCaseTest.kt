package com.bayu07750.mademovie.core.usecase

import com.bayu07750.mademovie.core.domain.usecase.GetTrendingMoviesPagingUseCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetTrendingMoviesPagingUseCaseTest : BaseUseCaseTest() {

    private lateinit var getTrendingMoviesPaginguseCase: GetTrendingMoviesPagingUseCase

    @Before
    fun setUp() {
        getTrendingMoviesPaginguseCase = GetTrendingMoviesPagingUseCase(movieRepository)
    }

    @Test
    fun `ensure invoke function is called`() {
        getTrendingMoviesPaginguseCase.invoke("day")

        Mockito.verify(movieRepository).fetchTrendingMovies("day")
    }
}