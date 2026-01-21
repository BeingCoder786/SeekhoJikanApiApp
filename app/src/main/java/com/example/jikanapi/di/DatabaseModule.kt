package com.example.jikanapi.di

import android.content.Context
import androidx.room.Room
import com.example.jikanapi.data.local.AnimeDao
import com.example.jikanapi.data.local.AnimeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AnimeDatabase =
        Room.databaseBuilder(context, AnimeDatabase::class.java, "anime_db").build()

    @Provides
    fun provideAnimeDao(db: AnimeDatabase): AnimeDao = db.animeDao()
}
