package com.bayu07750.mademovie.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.bayu07750.mademovie.core.domain.usecase.GetSearchMovieUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getSearchMovieUseCase: GetSearchMovieUseCase,
) : ViewModel() {

    private val _query = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies = _query
        .filter { it.isNotEmpty() }
        .flatMapLatest {
            getSearchMovieUseCase(it)
        }.cachedIn(viewModelScope)

    private var queryJob: Job? = null

    fun setQuery(query: String) {
        if (queryJob != null && queryJob?.isActive == true) {
            queryJob?.cancel()
            queryJob = null
        }
        queryJob = viewModelScope.launch {
            delay(600)
            _query.emit(query)
        }
    }
}