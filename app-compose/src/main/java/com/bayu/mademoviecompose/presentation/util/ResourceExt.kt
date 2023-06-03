package com.bayu.mademoviecompose.presentation.util

import com.bayu.mademoviecompose.presentation.UiState
import com.bayu07750.mademovie.core.data.Resource

fun <T> Resource<T>.toUiState(): UiState<T> {
    return when (this) {
        is Resource.Error -> UiState(isLoading = false, isError = true, message = message, data = null)
        is Resource.Loading -> UiState(isLoading = true, isError = false, message = null, data = null)
        is Resource.Success -> UiState(isLoading = false, isError = false, data = data, message = null)
    }
}