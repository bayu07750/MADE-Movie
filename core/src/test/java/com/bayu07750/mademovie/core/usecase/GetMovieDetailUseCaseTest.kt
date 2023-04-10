package com.bayu07750.mademovie.core.usecase

import com.bayu07750.mademovie.core.utils.DataDummy
import com.bayu07750.mademovie.core.data.Resource
import com.bayu07750.mademovie.core.domain.usecase.GetMovieDetailUseCase
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
class GetMovieDetailUseCaseTest : BaseUseCaseTest() {

    private lateinit var getMovieDetailUseCase: GetMovieDetailUseCase

    @Before
    fun setUp() {
        getMovieDetailUseCase = GetMovieDetailUseCase(movieRepository)
    }

    @Test
    fun `ensure invoke function is called`() = runTest {
        getMovieDetailUseCase.invoke(DataDummy.movie.id)

        Mockito.verify(movieRepository).fetchMovieDetail(DataDummy.movie.id)
    }

    @Test
    fun `get movie detail`() = runTest {
        val dummyMovie = flow {
            emit(Resource.Success(DataDummy.movieDetail))
        }
        Mockito.`when`(movieRepository.fetchMovieDetail(DataDummy.movie.id)).thenReturn(dummyMovie)

        val response = getMovieDetailUseCase.invoke(DataDummy.movie.id).firstOrNull()

        Assert.assertNotNull(response)
        Assert.assertTrue(response is Resource.Success)

        val data = response?.data

        Assert.assertNotNull(data)
        Assert.assertTrue(data?.id == DataDummy.movieDetail.id)
        Assert.assertEquals(DataDummy.movieDetail, data)
    }
}