package com.bayu07750.mademovie.core.usecase

import com.bayu07750.mademovie.core.utils.DataDummy
import com.bayu07750.mademovie.core.data.Resource
import com.bayu07750.mademovie.core.domain.usecase.GetBookmarkedMovieByIdUseCase
import com.bayu07750.mademovie.core.domain.usecase.GetMovieCastsUseCase
import com.bayu07750.mademovie.core.domain.usecase.GetMovieDetailAndCastUseCase
import com.bayu07750.mademovie.core.domain.usecase.GetMovieDetailUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetMovieDetailAndCastUseCaseTest : BaseUseCaseTest() {

    @Mock
    private lateinit var getMovieDetailUseCase: GetMovieDetailUseCase

    @Mock
    private lateinit var getMovieCastsUseCase: GetMovieCastsUseCase

    @Mock
    private lateinit var getBookmarkedMovieByIdUseCase: GetBookmarkedMovieByIdUseCase

    private lateinit var getMovieDetailAndCastUseCase: GetMovieDetailAndCastUseCase

    @Before
    fun setUp() {
        getMovieDetailAndCastUseCase = GetMovieDetailAndCastUseCase(
            getMovieDetailUseCase, getMovieCastsUseCase, getBookmarkedMovieByIdUseCase
        )
    }

    @Test
    fun `get movie detail and casts`() = runTest {
        val dummyMovieDetail = flow {
            emit(Resource.Success(DataDummy.movieDetail))
        }
        Mockito.`when`(getMovieDetailUseCase(DataDummy.movie.id)).thenReturn(dummyMovieDetail)

        val dummyCasts = flow {
            emit(Resource.Success(DataDummy.casts))
        }
        Mockito.`when`(getMovieCastsUseCase(DataDummy.movie.id)).thenReturn(dummyCasts)

        val dummyMovieBookmarked = flow {
            emit(DataDummy.movie.copy(bookmarked = true))
        }
        Mockito.`when`(getBookmarkedMovieByIdUseCase(DataDummy.movie.id)).thenReturn(dummyMovieBookmarked)

        val dummyMovieDetailAndCasts = dummyMovieDetail.first().data?.copy(
            casts = dummyCasts.first().data ?: emptyList(),
            bookmarked = dummyMovieBookmarked.first().bookmarked,
        )

        val response = getMovieDetailAndCastUseCase(DataDummy.movie.id).firstOrNull()

        Assert.assertNotNull(response)
        Assert.assertTrue(response is Resource.Success)

        val data = response?.data

        Assert.assertNotNull(data)
        Assert.assertTrue(data?.id == dummyMovieDetailAndCasts?.id)
        Assert.assertEquals(dummyMovieDetailAndCasts, data)
    }
}