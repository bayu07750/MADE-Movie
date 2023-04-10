package com.bayu07750.mademovie.core.usecase

import com.bayu07750.mademovie.core.domain.model.Genre
import com.bayu07750.mademovie.core.domain.usecase.GetAllMovieGenreUseCase
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
class GetAllMovieGenreUseCaseTest : BaseUseCaseTest() {

    private lateinit var getAllMovieGenreUseCase: GetAllMovieGenreUseCase

    @Before
    fun setUp() {
        getAllMovieGenreUseCase = GetAllMovieGenreUseCase(movieRepository)
    }

    @Test
    fun `ensure invoke function is called`() = runTest {
        getAllMovieGenreUseCase.invoke()

        Mockito.verify(movieRepository).getAllMovieGenre()
    }

    @Test
    fun `empty genres`() = runTest {
        val dummyGenres = flow<List<Genre>> { emit(emptyList()) }
        Mockito.`when`(movieRepository.getAllMovieGenre()).thenReturn(dummyGenres)

        val genres = getAllMovieGenreUseCase.invoke().firstOrNull()

        Assert.assertNotNull(genres)
        Assert.assertTrue(genres?.isEmpty() == true)
        Assert.assertEquals(dummyGenres.first().size, genres?.size)
        Assert.assertEquals(dummyGenres.first(), genres)
    }

    @Test
    fun `not empty genres`() = runTest {
        val dummyGenres = flow { emit(listOf(Genre(1, "Action", ""))) }
        Mockito.`when`(movieRepository.getAllMovieGenre()).thenReturn(dummyGenres)

        val genres = getAllMovieGenreUseCase.invoke().firstOrNull()

        Assert.assertNotNull(genres)
        Assert.assertTrue(genres?.isNotEmpty() == true)
        Assert.assertEquals(dummyGenres.first().size, genres?.size)
        Assert.assertEquals(dummyGenres.first(), genres)
    }
}