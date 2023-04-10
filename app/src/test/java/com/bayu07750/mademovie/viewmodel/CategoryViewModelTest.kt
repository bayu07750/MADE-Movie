package com.bayu07750.mademovie.viewmodel

import com.bayu07750.mademovie.core.domain.model.Genre
import com.bayu07750.mademovie.core.domain.usecase.GetAllMovieGenreUseCase
import com.bayu07750.mademovie.presentation.category.CategoryViewModel
import com.bayu07750.mademovie.utils.MainDispatcherRule
import com.bayu07750.mademovie.utils.TestMovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class CategoryViewModelTest {

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    private val testMovieRepository = TestMovieRepository()
    private val getAllMovieGenreUseCase = GetAllMovieGenreUseCase(testMovieRepository)

    private lateinit var viewModel: CategoryViewModel

    @Before
    fun setUp() {
        viewModel = CategoryViewModel(getAllMovieGenreUseCase)
    }

    @Test
    fun `genres when initialized should empty`() {
        Assert.assertEquals(emptyList<List<Genre>>(), viewModel.movieGenres.value)
    }

    @Test
    fun `genres should not empty`() = runTest {
        val dummyGenres = testMovieRepository.getAllMovieGenre().first()

        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.movieGenres.collect() }

        Assert.assertEquals(dummyGenres.size, viewModel.movieGenres.value.size)
        Assert.assertEquals(dummyGenres[0], viewModel.movieGenres.value[0])

        collectJob.cancel()
    }
}