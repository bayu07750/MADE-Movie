package com.bayu.mademoviecompose.presentation.home

import com.bayu.mademoviecompose.presentation.UiState
import com.bayu07750.mademovie.core.domain.model.Movie

data class HomeUiState(
    val trendingState: UiState<List<Movie>> = UiState.default(),
    val popularState: UiState<List<Movie>> = UiState.default(),
    val nowPlayingState: UiState<List<Movie>> = UiState.default(),
    val successChangeLanguage: Boolean = false,
    val trendingTimeWindow: TrendingTimeWindow = TrendingTimeWindow.WEEK,
)