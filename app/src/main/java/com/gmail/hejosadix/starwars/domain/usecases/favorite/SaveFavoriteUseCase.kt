package com.gmail.hejosadix.starwars.domain.usecases.favorite

import com.gmail.hejosadix.starwars.data.mappers.toFavorite
import com.gmail.hejosadix.starwars.domain.models.Person
import com.gmail.hejosadix.starwars.domain.repository.FavoriteRepository

class SaveFavoriteUseCase(
    val repository: FavoriteRepository,
) {
    suspend operator fun invoke(
        person: Person,
    ) {
        repository.insertFavorite(
            favorite = person.toFavorite(),
        )
    }
}