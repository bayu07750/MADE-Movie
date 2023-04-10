package com.bayu07750.mademovie.core.usecase

import com.bayu07750.mademovie.core.domain.model.Movie
import com.bayu07750.mademovie.core.domain.usecase.GetAllBookmarkedMovieUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class GetAllBookmarkedMovieUseCaseTest : BaseUseCaseTest() {

    private lateinit var getAllBookmarkedMovieUseCase: GetAllBookmarkedMovieUseCase

    @Before
    fun setUp() {
        getAllBookmarkedMovieUseCase = GetAllBookmarkedMovieUseCase(movieRepository)
    }

    @Test
    fun `ensure invoke function is called`() = runTest {
        getAllBookmarkedMovieUseCase.invoke()

        Mockito.verify(movieRepository).getAllBookmarkedMovie()
    }

    @Test
    fun `empty movies`() = runTest {
        val emptyMovies = flow<List<Movie>> { emit(emptyList()) }
        Mockito.`when`(movieRepository.getAllBookmarkedMovie()).thenReturn(emptyMovies)

        val movies = getAllBookmarkedMovieUseCase.invoke().firstOrNull()

        Assert.assertNotNull(movies)
        Assert.assertTrue(movies?.isEmpty() == true)
        Assert.assertEquals(emptyMovies.first().size, movies?.size)
        Assert.assertEquals(emptyMovies.first(), movies)
    }

    @Test
    fun `not empty movies`() = runTest {
        val dummyMovies = flow {
            emit(listOf(Movie(0, "", false)))
        }
        Mockito.`when`(movieRepository.getAllBookmarkedMovie()).thenReturn(dummyMovies)

        val movies = getAllBookmarkedMovieUseCase.invoke().firstOrNull()

        Assert.assertNotNull(movies)
        Assert.assertTrue(movies?.isNotEmpty() == true)
        Assert.assertEquals(dummyMovies.first().size, movies?.size)
        Assert.assertEquals(dummyMovies.firstOrNull(), movies)
    }
}