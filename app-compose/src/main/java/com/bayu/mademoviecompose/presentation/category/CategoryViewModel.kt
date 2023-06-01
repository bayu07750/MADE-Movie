package com.bayu.mademoviecompose.presentation.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayu.mademoviecompose.presentation.UiState
import com.bayu07750.mademovie.core.domain.model.Genre
import com.bayu07750.mademovie.core.domain.usecase.GetAllMovieGenreUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel(getAllMovieGenreUseCase: GetAllMovieGenreUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState.default<List<Genre>>())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = false) }
            getAllMovieGenreUseCase().collectLatest {  genres ->
                _uiState.update { it.copy(isLoading = false, isError = false, data = genres) }
            }
        }
    }
}