package com.gmail.hejosadix.starwars.domain.repository

import androidx.paging.PagingData
import com.gmail.hejosadix.starwars.data.common.StarWarsResult
import com.gmail.hejosadix.starwars.domain.models.Person
import kotlinx.coroutines.flow.Flow

interface PeopleRepository {
     fun getPeople(
    ): Flow<PagingData<Person>>

    suspend fun getPerson(
        id: String,
    ): Flow<StarWarsResult<Person>>
}