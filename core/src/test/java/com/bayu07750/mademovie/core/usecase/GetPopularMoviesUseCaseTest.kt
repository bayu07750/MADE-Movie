package com.bayu07750.mademovie.core.usecase

import com.bayu07750.mademovie.core.data.Resource
import com.bayu07750.mademovie.core.domain.usecase.GetPopularMoviesUseCase
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
class GetPopularMoviesUseCaseTest : BaseUseCaseTest() {

    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    @Before
    fun setUp() {
        getPopularMoviesUseCase = GetPopularMoviesUseCase(movieRepository)
    }

    @Test
    fun `ensure invoke function is called`() {
        getPopularMoviesUseCase.invoke(1)

        Mockito.verify(movieRepository).fetchPopularMovies(1)
    }

    @Test
    fun `get popular movie`() = runTest {
        val dummyResponse = flow {
            emit(Resource.Success(data = DataDummy.movies))
        }
        Mockito.`when`(movieRepository.fetchPopularMovies(1)).thenReturn(dummyResponse)

        val response = getPopularMoviesUseCase.invoke(1).firstOrNull()

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