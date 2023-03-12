package com.gmail.hejosadix.starwars.domain.usecases.favorite

import com.gmail.hejosadix.starwars.domain.repository.FavoriteRepository

class GetFavoriteUseCase(
    val repository: FavoriteRepository,
) {
    operator fun invoke(
        id: String,
    ) =
        repository.getFavorite(
            id = id,
        )

}