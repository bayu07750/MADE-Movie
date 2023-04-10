package com.bayu07750.mademovie.core.usecase

import com.bayu07750.mademovie.core.data.Resource
import com.bayu07750.mademovie.core.domain.usecase.GetTrendingMoviesUseCase
import com.bayu07750.mademovie.core.utils.DataDummy
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
class GetTrendingMoviesUseCaseTest : BaseUseCaseTest() {

    private lateinit var getPopularMoviesUseCase: GetTrendingMoviesUseCase

    @Before
    fun setUp() {
        getPopularMoviesUseCase = GetTrendingMoviesUseCase(movieRepository)
    }

    @Test
    fun `ensure invoke function is called`() {
        getPopularMoviesUseCase.invoke("day", 1)

        Mockito.verify(movieRepository).fetchTrendingMovies("day", 1)
    }

    @Test
    fun `get trending movie`() = runTest {
        val dummyResponse = flow {
            emit(Resource.Success(data = DataDummy.movies))
        }
        Mockito.`when`(movieRepository.fetchTrendingMovies("day", 1)).thenReturn(dummyResponse)

        val response = getPopularMoviesUseCase.invoke("day", 1).firstOrNull()

        Assert.assertNotNull(response)
        Assert.assertTrue(response is Resource.Success)

        val data = response?.data

        Assert.assertNotNull(data)
        Assert.assertTrue(data?.isNotEmpty() == true)
        Assert.assertEquals(dummyResponse.first().data?.size, data?.size)
        data?.forEachIndexed { index, movie ->
            Assert.assertEquals(dummyResponse.first().data?.get(index), movie)
        }
    }
}