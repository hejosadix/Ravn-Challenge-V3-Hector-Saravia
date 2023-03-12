package com.gmail.hejosadix.starwars.domain.repository

import com.gmail.hejosadix.starwars.data.common.StarWarsResult
import com.gmail.hejosadix.starwars.domain.models.Person
import com.gmail.hejosadix.starwars.domain.models.StarWarsPeople
import kotlinx.coroutines.flow.Flow

interface PeopleRepository {
    suspend fun getPeople(
        first: Int, after: String,
    ): Flow<StarWarsResult<StarWarsPeople>>

    suspend fun getPerson(
        id: String,
    ): Flow<StarWarsResult<Person>>
}