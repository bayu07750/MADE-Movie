package com.bayu.mademoviecompose.presentation.discovery

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bayu.mademoviecompose.presentation.PARAM_GENRE_ID
import com.bayu.mademoviecompose.presentation.PARAM_TRENDING_TIME_WINDOW
import com.bayu.mademoviecompose.presentation.PARAM_TYPE
import com.bayu07750.mademovie.core.domain.model.Movie
import com.bayu07750.mademovie.core.domain.usecase.GetMovieDiscoveryUseCase
import com.bayu07750.mademovie.core.domain.usecase.GetNowPlayingMoviesPagingUseCase
import com.bayu07750.mademovie.core.domain.usecase.GetPopularMoviesPagingUseCase
import com.bayu07750.mademovie.core.domain.usecase.GetTrendingMoviesPagingUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class DiscoveryViewModel(
    savedStateHandle: SavedStateHandle,
    private val getTrendingMoviesPagingUseCase: GetTrendingMoviesPagingUseCase,
    private val getPopularMoviesPagingUseCase: GetPopularMoviesPagingUseCase,
    private val getNowPlayingMoviesPagingUseCase: GetNowPlayingMoviesPagingUseCase,
    private val getMovieDiscoveryUseCase: GetMovieDiscoveryUseCase,
) : ViewModel() {

    private val _discover = MutableStateFlow<Discover?>(null)

    fun setDiscover(discover: Discover?) {
        _discover.value = discover
    }

    init {
        val type = DiscoveryType.valueOf(savedStateHandle.get<String>(PARAM_TYPE) ?: DiscoveryType.POPULAR.name)

        val genre = savedStateHandle.get<Int>(PARAM_GENRE_ID) ?: -1
        val timeWindow = savedStateHandle.get<String>(PARAM_TRENDING_TIME_WINDOW)

        when (type) {
            DiscoveryType.GENRE -> {
                setDiscover(Discover.ByGenre(genre))
            }

            DiscoveryType.TRENDING -> {
                if (!timeWindow.isNullOrEmpty()) {
                    setDiscover(Discover.Trending(timeWindow))
                }
            }

            DiscoveryType.POPULAR -> setDiscover(Discover.Popular)
            DiscoveryType.NOW_PLAYING -> setDiscover(Discover.NowPlaying)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies: Flow<PagingData<Movie>> = _discover
        .filterNotNull()
        .flatMapLatest {
            when (it) {
                is Discover.ByGenre -> getMovieDiscoveryUseCase(it.genreId)
                Discover.NowPlaying -> getNowPlayingMoviesPagingUseCase()
                Discover.Popular -> getPopularMoviesPagingUseCase()
                is Discover.Trending -> getTrendingMoviesPagingUseCase(it.timeWindow)
            }
        }.cachedIn(viewModelScope)
}