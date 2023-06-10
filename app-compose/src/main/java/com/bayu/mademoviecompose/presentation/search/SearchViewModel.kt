package com.bayu.mademoviecompose.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.bayu07750.mademovie.core.domain.usecase.GetSearchMovieUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getSearchMovieUseCase: GetSearchMovieUseCase,
) : ViewModel() {

    private val _query = MutableStateFlow("a")

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies = _query
        .filter { it.isNotEmpty() }
        .flatMapLatest {
            getSearchMovieUseCase(it)
        }.cachedIn(viewModelScope)

    fun setQuery(query: String) {
        viewModelScope.launch {
            _query.emit(query)
        }
    }
}