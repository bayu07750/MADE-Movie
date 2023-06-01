package com.bayu.mademoviecompose.presentation

data class UiState<T>(
    val isLoading: Boolean,
    val isError: Boolean,
    val message: String?,
    val data: T?,
) {

    companion object {
        fun <T> default() = UiState<T>(
            isLoading = false,
            isError = false,
            message = null,
            data = null
        )
    }
}