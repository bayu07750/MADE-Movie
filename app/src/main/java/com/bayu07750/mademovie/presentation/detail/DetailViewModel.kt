package com.bayu07750.mademovie.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayu07750.mademovie.core.domain.model.Movie
import com.bayu07750.mademovie.core.domain.model.MovieDetail
import com.bayu07750.mademovie.core.domain.usecase.BookmarkMovieUseCase
import com.bayu07750.mademovie.core.domain.usecase.GetMovieDetailAndCastUseCase
import com.bayu07750.mademovie.presentation.UiState
import com.bayu07750.mademovie.presentation.util.extension.toUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailAndCastUseCase: GetMovieDetailAndCastUseCase,
    private val bookmarkMovieUseCase: BookmarkMovieUseCase,
) : ViewModel() {

    init {
        savedStateHandle.get<Movie>("movie")?.let { movie ->
            getDetailMovie(movieId = movie.id)
        }
    }

    private val _uiState = MutableStateFlow<UiState<MovieDetail>>(UiState.default())
    val uiState = _uiState.asStateFlow()

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