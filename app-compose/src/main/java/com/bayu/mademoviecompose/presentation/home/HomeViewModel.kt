package com.bayu.mademoviecompose.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayu.mademoviecompose.presentation.util.toUiState
import com.bayu07750.mademovie.core.data.local.mmkv.Session
import com.bayu07750.mademovie.core.domain.usecase.GetNowPlayingMoviesUseCase
import com.bayu07750.mademovie.core.domain.usecase.GetPopularMoviesUseCase
import com.bayu07750.mademovie.core.domain.usecase.GetTrendingMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val session: Session,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _trendingTimeWindow = MutableStateFlow(TrendingTimeWindow.WEEK)
    val trendingTimeWindow = _trendingTimeWindow.asStateFlow()

    init {
        getData()
    }

    fun getData() {
        getTrendingMovies(trendingTimeWindow.value.timeWindow)
        getPopularMovies()
        getNowPlayingMovies()
    }

    private fun getTrendingMovies(timeWindow: String) {
        getTrendingMoviesUseCase.invoke(timeWindow, 1).onEach { result ->
            _uiState.update { it.copy(trendingState = result.toUiState()) }
        }.launchIn(viewModelScope)
    }

    private fun getPopularMovies() {
        getPopularMoviesUseCase.invoke(1).onEach { result ->
            _uiState.update { it.copy(popularState = result.toUiState()) }
        }.launchIn(viewModelScope)
    }

    private fun getNowPlayingMovies() {
        getNowPlayingMoviesUseCase.invoke(1).onEach { result ->
            _uiState.update { it.copy(nowPlayingState = result.toUiState()) }
        }.launchIn(viewModelScope)
    }

    fun setTrendingTimeWindow(value: TrendingTimeWindow) {
        _trendingTimeWindow.update { prev ->
            if (prev == value) return
            getTrendingMovies(timeWindow = value.timeWindow)
            value
        }
    }
}