package com.gmail.hejosadix.starwars.domain.usecases.people

import com.gmail.hejosadix.starwars.domain.usecases.favorite.DeleteFavoriteUseCase
import com.gmail.hejosadix.starwars.domain.usecases.favorite.GetFavoriteUseCase
import com.gmail.hejosadix.starwars.domain.usecases.favorite.SaveFavoriteUseCase

data class PersonUseCases(
    val getPersonUseCase: GetPersonUseCase,
    val getFavoriteUseCase: GetFavoriteUseCase,
    val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    val saveFavoriteUseCase: SaveFavoriteUseCase,
)
