package com.bayu07750.mademovie.core.usecase

import com.bayu07750.mademovie.core.data.Resource
import com.bayu07750.mademovie.core.domain.model.Cast
import com.bayu07750.mademovie.core.domain.model.Movie
import com.bayu07750.mademovie.core.domain.usecase.GetMovieCastsUseCase
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

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetMovieCastsUseCaseTest : BaseUseCaseTest() {

    private val dummyMovie = Movie(100, "testing raw", true)
    private val dummyCasts = Cast(10, "Test", "testing taw", "Test")
    private val dummySuccessCasts = flow { emit(Resource.Success(listOf(dummyCasts))) }

    private lateinit var getMovieCastsUseCase: GetMovieCastsUseCase

    @Before
    fun setUp() {
        getMovieCastsUseCase = GetMovieCastsUseCase(movieRepository)
    }

    @Test
    fun `ensure invoke function is called`() = runTest {
        getMovieCastsUseCase.invoke(dummyMovie.id)

        Mockito.verify(movieRepository).fetchMovieCasts(dummyMovie.id)
    }

    @Test
    fun `success casts`() = runTest {
        Mockito.`when`(movieRepository.fetchMovieCasts(dummyMovie.id)).thenReturn(dummySuccessCasts)

        val response = getMovieCastsUseCase.invoke(dummyMovie.id).firstOrNull()

        Assert.assertNotNull(response)
        Assert.assertTrue(response is Resource.Success)

        val data = response?.data

        Assert.assertNotNull(data)
        Assert.assertTrue(data?.isNotEmpty() == true)
        Assert.assertEquals(dummySuccessCasts.first().data?.size, data?.size)
        data?.forEach { cast ->
            dummySuccessCasts.first().data?.forEach { dummyCast ->
                Assert.assertEquals(dummyCast, cast)
            }
        }
    }
}