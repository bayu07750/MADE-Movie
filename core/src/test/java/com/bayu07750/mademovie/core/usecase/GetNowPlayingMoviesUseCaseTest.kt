package com.bayu07750.mademovie.core.usecase

import com.bayu07750.mademovie.core.data.Resource
import com.bayu07750.mademovie.core.domain.usecase.GetNowPlayingMoviesUseCase
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
class GetNowPlayingMoviesUseCaseTest : BaseUseCaseTest() {

    private lateinit var getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase

    @Before
    fun setUp() {
        getNowPlayingMoviesUseCase = GetNowPlayingMoviesUseCase(movieRepository)
    }

    @Test
    fun `ensure invoke function is called`() {
        getNowPlayingMoviesUseCase.invoke(1)

        Mockito.verify(movieRepository).fetchNowPlayingMovies(1)
    }

    @Test
    fun `get now playing movie`() = runTest {
        val dummyResponse = flow {
            emit(Resource.Success(data = DataDummy.movies))
        }
        Mockito.`when`(movieRepository.fetchNowPlayingMovies(1)).thenReturn(dummyResponse)

        val response = getNowPlayingMoviesUseCase.invoke(1).firstOrNull()

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