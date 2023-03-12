package com.gmail.hejosadix.starwars.domain.repository

import com.gmail.hejosadix.starwars.data.local.entity.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavorite(
        id: String,
    ): Flow<Favorite?>

    suspend fun insertFavorite(
        favorite: Favorite,
    ): Long

    suspend fun deleteFavorite(
        id: String,
    )
}