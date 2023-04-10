package com.bayu07750.mademovie.core.usecase

import com.bayu07750.mademovie.core.domain.model.Movie
import com.bayu07750.mademovie.core.domain.usecase.GetBookmarkedMovieByIdUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetBookmarkedMovieByIdUseCaseTest : BaseUseCaseTest() {

    private val dummyMovie = Movie(100, "testing raw", true)

    private lateinit var getBookmarkedMovieByIdUseCase: GetBookmarkedMovieByIdUseCase

    @Before
    fun setUp() {
        getBookmarkedMovieByIdUseCase = GetBookmarkedMovieByIdUseCase(movieRepository)
    }

    @Test
    fun `ensure invoke function is called`() = runTest {
        getBookmarkedMovieByIdUseCase.invoke(dummyMovie.id)

        Mockito.verify(movieRepository).getBookmarkedMovieById(dummyMovie.id)
    }

    @Test
    fun `get bookmarked movie by id`() = runTest {
        Mockito.`when`(movieRepository.getBookmarkedMovieById(dummyMovie.id)).thenReturn(flow { emit(dummyMovie) })
        val movie = movieRepository.getBookmarkedMovieById(dummyMovie.id).firstOrNull()

        Assert.assertNotNull(movie)
        Assert.assertEquals(dummyMovie.id, movie?.id)
        Assert.assertEquals(dummyMovie.posterRaw, movie?.posterRaw)
        Assert.assertEquals(dummyMovie.bookmarked, movie?.bookmarked)
    }
}