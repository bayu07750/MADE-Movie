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
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val session: Session,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _trendingTimeWindow = MutableStateFlow(_uiState.value.trendingTimeWindow)

    init {
        getData()
    }

    fun getData() {
        getTrendingMovies(_trendingTimeWindow.value.timeWindow)
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
        viewModelScope.launch {
            _uiState.update { it.copy(trendingTimeWindow = value) }
            getTrendingMovies(timeWindow = value.timeWindow)
        }
    }

    fun setLanguage(lang: String) {
        viewModelScope.launch {
            val currentLang = session.getString(Session.KEY_LANGUAGE)
            if (lang == currentLang) return@launch
            _uiState.update { it.copy(successChangeLanguage = session.setValue(Session.KEY_LANGUAGE, lang)) }
        }
    }
}