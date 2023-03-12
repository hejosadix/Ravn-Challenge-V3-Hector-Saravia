package com.gmail.hejosadix.starwars.domain.usecases.favorite

import com.gmail.hejosadix.starwars.domain.repository.FavoriteRepository

class DeleteFavoriteUseCase(
    val repository: FavoriteRepository,
) {
    suspend operator fun invoke(
        id: String,
    ) {
        repository.deleteFavorite(
            id = id,
        )
    }
}