package com.bayu.mademoviecompose.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayu.mademoviecompose.presentation.PARAM_MOVIE
import com.bayu.mademoviecompose.presentation.UiState
import com.bayu.mademoviecompose.presentation.util.toUiState
import com.bayu07750.mademovie.core.domain.model.MovieDetail
import com.bayu07750.mademovie.core.domain.usecase.BookmarkMovieUseCase
import com.bayu07750.mademovie.core.domain.usecase.GetMovieDetailAndCastUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailAndCastUseCase: GetMovieDetailAndCastUseCase,
    private val bookmarkMovieUseCase: BookmarkMovieUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<MovieDetail>>(UiState.default())
    val uiState = _uiState.asStateFlow()

    init {
        savedStateHandle.get<Int>(PARAM_MOVIE)?.let { movieId ->
            if (movieId != -1) {
                getDetailMovie(movieId = movieId)
            } else {
                _uiState.update { it.copy(isLoading = false, isError = true, message = "Movie id cannot be empty") }
            }
        }
    }

    fun getDetailMovie(movieId: Int) {
        getMovieDetailAndCastUseCase.invoke(movieId).onEach { result ->
            _uiState.update { result.toUiState() }
        }.launchIn(viewModelScope)
    }

    fun bookmarkMovie(movieDetail: MovieDetail) {
        viewModelScope.launch {
            bookmarkMovieUseCase(movieDetail.asMovie())
            _uiState.update { prev ->
                prev.copy(data = prev.data?.copy(bookmarked = !movieDetail.bookmarked))
            }
        }
    }
}