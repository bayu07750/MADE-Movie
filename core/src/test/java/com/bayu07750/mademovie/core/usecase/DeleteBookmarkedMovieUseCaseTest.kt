package com.bayu07750.mademovie.core.usecase

import com.bayu07750.mademovie.core.domain.model.Movie
import com.bayu07750.mademovie.core.domain.usecase.DeleteBookmarkedMovieUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class DeleteBookmarkedMovieUseCaseTest : BaseUseCaseTest() {

    private lateinit var deleteBookmarkedMovieUseCase: DeleteBookmarkedMovieUseCase

    @Before
    fun setUp() {
        deleteBookmarkedMovieUseCase = DeleteBookmarkedMovieUseCase(movieRepository)
    }

    @Test
    fun `ensure invoke function is called`() = runTest {
        val movie = Movie(1, "test", true)
        deleteBookmarkedMovieUseCase.invoke(movie)

        Mockito.verify(movieRepository).bookmarkMovie(movie, true)
    }
}