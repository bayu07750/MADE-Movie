package com.bayu07750.mademovie.bookmark.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayu07750.mademovie.core.domain.usecase.GetAllBookmarkedMovieUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class BookmarkViewModel(
    getAllBookmarkedMovieUseCase: GetAllBookmarkedMovieUseCase,
) : ViewModel() {

    val bookmarkedMovies = getAllBookmarkedMovieUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}