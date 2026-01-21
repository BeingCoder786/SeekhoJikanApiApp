package com.example.jikanapi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
 data class AnimeEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val episodes: Int,
    val score: Double,
    val imageUrl: String
)

