package com.bayu07750.mademovie.presentation.home

import com.bayu07750.mademovie.core.domain.model.Movie
import com.bayu07750.mademovie.presentation.UiState

data class HomeUiState(
    val trendingState: UiState<List<Movie>> = UiState.default(),
    val popularState: UiState<List<Movie>> = UiState.default(),
    val nowPlayingState: UiState<List<Movie>> = UiState.default()
) {
    val isError get() = trendingState.isError || trendingState.isError || nowPlayingState.isError
    val isLoading get() = trendingState.isLoading || popularState.isLoading || nowPlayingState.isLoading
    val isSuccess get() = !isError && !isLoading
    val trendingMovies get() = trendingState.data ?: emptyList()
    val popularMovies get() = popularState.data ?: emptyList()
    val nowPlayingMovies get() = nowPlayingState.data ?: emptyList()
}