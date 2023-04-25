package com.gmail.hejosadix.starwars.data.remote.datasource



import androidx.paging.PagingData
import com.gmail.hejosadix.GetPersonQuery
import com.gmail.hejosadix.starwars.data.common.StarWarsResult
import com.gmail.hejosadix.starwars.domain.models.Person
import kotlinx.coroutines.flow.Flow
interface PeopleRemoteDataSource {
      fun getPeople(
    ): Flow<PagingData<Person>>

    suspend fun getPerson(
        id: String,
    ): StarWarsResult<GetPersonQuery.Person?>
}
