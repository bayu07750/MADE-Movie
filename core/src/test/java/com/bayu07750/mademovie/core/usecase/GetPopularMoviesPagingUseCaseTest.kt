package com.bayu07750.mademovie.core.usecase

import com.bayu07750.mademovie.core.domain.usecase.GetPopularMoviesPagingUseCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetPopularMoviesPagingUseCaseTest : BaseUseCaseTest() {

    private lateinit var getPopularMoviesPagingUseCase: GetPopularMoviesPagingUseCase

    @Before
    fun setUp() {
        getPopularMoviesPagingUseCase = GetPopularMoviesPagingUseCase(movieRepository)
    }

    @Test
    fun `ensure invoke function is called`() {
        getPopularMoviesPagingUseCase.invoke()

        Mockito.verify(movieRepository).fetchPopularMovies()
    }
}