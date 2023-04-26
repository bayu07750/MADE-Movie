package com.bayu07750.mademovie.presentation.discovery

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bayu07750.mademovie.core.domain.model.Genre
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
        val type = savedStateHandle.get<DiscoveryType>("type")
        val genre = savedStateHandle.get<Genre>("genre")
        val timeWindow = savedStateHandle.get<String>("timeWindow")

        when (type) {
            DiscoveryType.GENRE -> {
                if (genre != null) {
                    setDiscover(Discover.ByGenre(genre.id))
                }
            }
            DiscoveryType.TRENDING -> {
                if (!timeWindow.isNullOrEmpty()) {
                    setDiscover(Discover.Trending(timeWindow))
                }
            }
            DiscoveryType.POPULAR -> setDiscover(Discover.Popular)
            DiscoveryType.NOW_PLAYING -> setDiscover(Discover.NowPlaying)
            null -> _discover.value = null
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