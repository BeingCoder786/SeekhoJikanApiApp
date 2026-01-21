package com.example.jikanapi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jikanapi.data.remote.AnimeDetail
import com.example.jikanapi.data.repository.AnimeRepository
import com.example.jikanapi.ui.AnimeDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AnimeDetailState>(AnimeDetailState.Loading)
    val uiState: StateFlow<AnimeDetailState> = _uiState

    fun loadAnimeDetail(animeId: Int) {
        viewModelScope.launch {
            _uiState.value = AnimeDetailState.Loading
            try {
                val detail = repository.getAnimeDetail(animeId)
                _uiState.value = AnimeDetailState.Success(detail)
            } catch (e: Exception) {
                _uiState.value = AnimeDetailState.Error(e.localizedMessage ?: "Something went wrong")
            }
        }
    }
}


