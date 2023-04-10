package com.bayu07750.mademovie.core.usecase

import com.bayu07750.mademovie.core.domain.usecase.GetSearchMovieUseCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetSearchMovieUseCaseTest : BaseUseCaseTest() {

    private lateinit var getSearchMovieUseCase: GetSearchMovieUseCase

    @Before
    fun setUp() {
        getSearchMovieUseCase = GetSearchMovieUseCase(movieRepository)
    }

    @Test
    fun `ensure invoke function is called`() {
        val query = "naruto"
        getSearchMovieUseCase.invoke(query)

        Mockito.verify(movieRepository).fetchSearchMovie(query)
    }
}