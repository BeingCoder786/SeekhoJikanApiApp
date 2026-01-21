package com.example.jikanapi.data.repository

import android.util.Log
import com.example.jikanapi.data.local.AnimeDao
import com.example.jikanapi.data.local.AnimeEntity
import com.example.jikanapi.data.remote.AnimeApiService
import com.example.jikanapi.data.remote.AnimeDetail
import com.example.jikanapi.model.Anime
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class AnimeRepository @Inject constructor(
    private val api: AnimeApiService,
    private val dao: AnimeDao
) {

    suspend fun getTopAnime(): List<AnimeEntity> {
        return try {
            val response = api.getTopAnime()
            Log.d("API", "Top anime response size = ${response.data.size}")
            Log.d("API", "Top anime response  = ${response.data}")
            val entities = response.data.map { it.toEntity() }
            dao.insertAll(entities)

            entities
        } catch (e: Exception) {
            Log.e("API", "API failed, loading from DB", e)
            Log.d("Apifail",e.message.toString())
             dao.getAllAnimeOnce()
        }
    }

    suspend fun getAnimeDetail(id: Int): AnimeDetail {
        return try {
            val response = api.getAnimeDetails(id)
            response.data
        } catch (e: Exception) {
            Log.e("API", "API failed", e)

            throw e
        }
    }
}

fun Anime.toEntity() = AnimeEntity(
    id = malId,
    title = title,
    episodes = episodes ?: 0,
    score = score ?: 0.0,
    imageUrl = images.jpg.imageUrl
)
