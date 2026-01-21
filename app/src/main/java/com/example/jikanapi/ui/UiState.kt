package com.example.jikanapi.ui

import com.example.jikanapi.data.remote.AnimeDetail

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}


sealed class AnimeDetailState {
    object Loading : AnimeDetailState()
    data class Success(val data: AnimeDetail) : AnimeDetailState()
    data class Error(val message: String) : AnimeDetailState()
}
