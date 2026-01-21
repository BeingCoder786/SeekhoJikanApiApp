package com.example.jikanapi.ui.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.jikanapi.viewmodel.AnimeViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.jikanapi.ui.AnimeDetailState
import com.example.jikanapi.viewmodel.AnimeDetailViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView

@Composable
fun DetailScreen(animeId: Int, viewModel: AnimeDetailViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(animeId) { viewModel.loadAnimeDetail(animeId) }

    when (uiState) {
        is AnimeDetailState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center // Centers the content
            ) {
                CircularProgressIndicator() // The spinning loader
            }
        }
        is AnimeDetailState.Success -> {
            val anime = (uiState as AnimeDetailState.Success).data
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    AsyncImage(
                        model = anime.images.jpg.image_url,
                        contentDescription = anime.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(anime.title_english ?: anime.title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text("Episodes: ${anime.episodes ?: "N/A"}")
                    Text("Score: ${anime.score ?: "N/A"}")
                    Spacer(Modifier.height(12.dp))
                    Text("Synopsis", fontWeight = FontWeight.Bold)
                    Text(anime.synopsis ?: "No description available")
                    Spacer(Modifier.height(12.dp))
                    Text("Genres", fontWeight = FontWeight.Bold)
                    Text(anime.genres.joinToString { it.name })
                    Spacer(Modifier.height(12.dp))
                    if (anime.studios.isNotEmpty()) {
                        Text("Studios", fontWeight = FontWeight.Bold)
                        Text(anime.studios.joinToString { it.name })
                        Spacer(Modifier.height(12.dp))
                    }
                    TrailerPlayer(trailerUrl = anime.trailer?.embed_url, posterUrl = anime.images.jpg.image_url)

                }
            }
        }
        is AnimeDetailState.Error -> {
            val message = (uiState as AnimeDetailState.Error).message
            Text("Error: $message", color = androidx.compose.ui.graphics.Color.Red, modifier = Modifier.padding(16.dp))
        }
    }
}



@Composable
fun TrailerPlayer(trailerUrl: String?, posterUrl: String) {
    if (!trailerUrl.isNullOrEmpty()) {
        val context = LocalContext.current
        val exoPlayer = remember {
            ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(Uri.parse(trailerUrl)))
                prepare()
                playWhenReady = false
            }
        }

        DisposableEffect(Unit) {
            onDispose {
                exoPlayer.release()
            }
        }

        AndroidView(
            factory = {
                PlayerView(it).apply {
                    player = exoPlayer
                    useController = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

    } else {
        AsyncImage(
            model = posterUrl,
            contentDescription = "Poster",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
    }
}



