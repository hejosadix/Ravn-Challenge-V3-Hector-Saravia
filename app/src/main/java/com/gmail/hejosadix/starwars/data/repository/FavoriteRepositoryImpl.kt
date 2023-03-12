package com.gmail.hejosadix.starwars.data.repository

import com.gmail.hejosadix.starwars.data.local.dao.FavoriteDao
import com.gmail.hejosadix.starwars.data.local.entity.Favorite
import com.gmail.hejosadix.starwars.domain.repository.FavoriteRepository


class FavoriteRepositoryImpl constructor(
    val local: FavoriteDao,
) : FavoriteRepository {
    override  fun getFavorite(id: String) = local.favorite(
        id = id,
    )

    override suspend fun insertFavorite(
        favorite: Favorite,
    ) =
        local.insert(
            model = favorite,
        )


    override suspend fun deleteFavorite(
        id: String,
    ) = local.deleteById(
        id = id,
    )
}