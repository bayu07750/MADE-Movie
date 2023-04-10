package com.bayu07750.mademovie.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayu07750.mademovie.core.data.local.mmkv.Session
import com.bayu07750.mademovie.core.domain.usecase.GetNowPlayingMoviesUseCase
import com.bayu07750.mademovie.core.domain.usecase.GetPopularMoviesUseCase
import com.bayu07750.mademovie.core.domain.usecase.GetTrendingMoviesUseCase
import com.bayu07750.mademovie.presentation.util.extension.toUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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

    private val _languageState = Channel<Boolean>()
    val languageState = _languageState.receiveAsFlow()

    fun setLanguage(lang: String) {
        viewModelScope.launch {
            val currentLang = session.getString(Session.KEY_LANGUAGE)
            if (lang == currentLang) return@launch
            _languageState.send(
                session.setValue(
                    Session.KEY_LANGUAGE,
                    lang
                )
            )
        }
    }
}