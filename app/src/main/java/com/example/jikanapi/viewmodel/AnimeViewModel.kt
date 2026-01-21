package com.example.jikanapi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jikanapi.data.local.AnimeEntity
import com.example.jikanapi.data.repository.AnimeRepository
import com.example.jikanapi.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _animeList =
        MutableStateFlow<UiState<List<AnimeEntity>>>(UiState.Loading)
    val animeList: StateFlow<UiState<List<AnimeEntity>>> = _animeList

    init {
        fetchAnime()
    }

    private fun fetchAnime() {
        viewModelScope.launch {
            _animeList.value = UiState.Loading
            try {
                val list = repository.getTopAnime()
                _animeList.value = UiState.Success(list)
            } catch (e: Exception) {
                _animeList.value = UiState.Error(e.message ?: "Something went wrong")
            }
        }
    }
}

