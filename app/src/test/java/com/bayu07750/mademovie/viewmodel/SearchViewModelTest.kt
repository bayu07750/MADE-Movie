package com.bayu07750.mademovie.viewmodel

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import com.bayu07750.mademovie.core.domain.usecase.GetSearchMovieUseCase
import com.bayu07750.mademovie.presentation.search.SearchViewModel
import com.bayu07750.mademovie.utils.DataDummy
import com.bayu07750.mademovie.utils.MainDispatcherRule
import com.bayu07750.mademovie.utils.MovieItemCallback
import com.bayu07750.mademovie.utils.NooListUpdateCallback
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var getSearchMovieUseCase: GetSearchMovieUseCase

    @Test
    fun `search movies`() = runTest {
        val searchViewModel = SearchViewModel(getSearchMovieUseCase)

        val movies = DataDummy.generateDummyMovies()
        val dummyPagingData = PagingData.from(movies)
        val dummyPagingDataFlow = flow { emit(dummyPagingData) }

        val query = "naruto"

        Mockito.`when`(getSearchMovieUseCase.invoke(query)).thenReturn(dummyPagingDataFlow)

        searchViewModel.setQuery(query)

        val response = searchViewModel.movies.firstOrNull() ?: PagingData.empty()

        val differ = AsyncPagingDataDiffer(
            diffCallback = MovieItemCallback(),
            updateCallback = NooListUpdateCallback(),
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(response)

        TestCase.assertNotNull(differ.snapshot())
        Assert.assertEquals(movies.size, differ.snapshot().size)
        Assert.assertEquals(movies[0], differ.snapshot().items[0])
    }
}