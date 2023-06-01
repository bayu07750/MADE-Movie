package com.bayu.mademoviecompose.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayu.mademoviecompose.presentation.UiState
import com.bayu07750.mademovie.core.domain.model.Movie
import com.bayu07750.mademovie.core.domain.usecase.GetAllBookmarkedMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookmarkViewModel(getAllBookmarkedMovieUseCase: GetAllBookmarkedMovieUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState.default<List<Movie>>())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = false) }
            getAllBookmarkedMovieUseCase.invoke().collectLatest { movies ->
                _uiState.update { it.copy(isLoading = false, isError = false, data = movies) }
            }
        }
    }
}