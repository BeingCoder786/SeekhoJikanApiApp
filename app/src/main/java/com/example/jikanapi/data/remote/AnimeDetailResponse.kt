package com.example.jikanapi.data.remote


import com.google.gson.annotations.SerializedName

data class AnimeDetailResponse(
    val data: AnimeDetail
)

data class AnimeDetail(
    val mal_id: Int,
    val title: String,
    val title_english: String?,
    val synopsis: String?,
    val episodes: Int?,
    val score: Double?,
    val genres: List<Genre>,
    val trailer: Trailer?,
    val images: Images,
    val studios: List<Studio>
)

data class Genre(
    val name: String
)

data class Trailer(
    val embed_url: String?
)

data class Images(
    val jpg: ImageUrls
)

data class ImageUrls(
    val image_url: String
)

data class Studio(
    val name: String
)

