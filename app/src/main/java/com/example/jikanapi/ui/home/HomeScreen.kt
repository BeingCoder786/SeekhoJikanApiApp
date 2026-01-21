package com.example.jikanapi.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jikanapi.data.local.AnimeEntity
import com.example.jikanapi.ui.UiState
import com.example.jikanapi.viewmodel.AnimeViewModel

@Composable
fun HomeScreen(
    viewModel: AnimeViewModel = hiltViewModel(),
    onClick: (Int) -> Unit
) {
    val state by viewModel.animeList.collectAsState()

    when (state) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Error -> {
            Text(text = (state as UiState.Error).message)
        }

        is UiState.Success -> {
            val animeList = (state as UiState.Success<List<AnimeEntity>>).data
            LazyColumn {
                items(animeList) { anime ->
                    AnimeItem(anime) { onClick(anime.id) }
                }
            }
        }
    }
}

