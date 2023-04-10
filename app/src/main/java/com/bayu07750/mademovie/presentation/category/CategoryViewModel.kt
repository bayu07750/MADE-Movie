package com.bayu07750.mademovie.presentation.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayu07750.mademovie.core.domain.model.Genre
import com.bayu07750.mademovie.core.domain.usecase.GetAllMovieGenreUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class CategoryViewModel(getAllMovieGenreUseCase: GetAllMovieGenreUseCase) : ViewModel() {

    val movieGenres: StateFlow<List<Genre>> =
        getAllMovieGenreUseCase().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}