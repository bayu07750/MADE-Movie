package com.bayu07750.mademovie.core.usecase

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import com.bayu07750.mademovie.core.utils.DataDummy
import com.bayu07750.mademovie.core.utils.MainDispatcherRule
import com.bayu07750.mademovie.core.utils.MovieItemCallback
import com.bayu07750.mademovie.core.utils.NooListUpdateCallback
import com.bayu07750.mademovie.core.domain.usecase.GetMovieDiscoveryUseCase
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetMovieDiscoveryUseCaseTest : BaseUseCaseTest() {

    private lateinit var getMovieDiscoveryUseCase: GetMovieDiscoveryUseCase

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Before
    fun setUp() {
        getMovieDiscoveryUseCase = GetMovieDiscoveryUseCase(movieRepository)
    }

    @Test
    fun `ensure invoke function is called`() {
        getMovieDiscoveryUseCase.invoke(1)

        Mockito.verify(movieRepository).fetchMovieDiscovery(1)
    }

    @Test
    fun `get movie discovery`() = runTest {
        val movies = DataDummy.generateDummyMovies()
        val dummyPagingData = PagingData.from(movies)
        val dummyPagingDataFlow = flow { emit(dummyPagingData) }

        Mockito.`when`(movieRepository.fetchMovieDiscovery(1)).thenReturn(dummyPagingDataFlow)

        val response = getMovieDiscoveryUseCase.invoke(1).firstOrNull() ?: PagingData.empty()

        val differ = AsyncPagingDataDiffer(
            diffCallback = MovieItemCallback(),
            updateCallback = NooListUpdateCallback(),
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(response)

        assertNotNull(differ.snapshot())
        assertEquals(movies.size, differ.snapshot().size)
        assertEquals(movies[0], differ.snapshot().items[0])
    }
}