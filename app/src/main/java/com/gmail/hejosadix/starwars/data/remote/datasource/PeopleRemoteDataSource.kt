package com.gmail.hejosadix.starwars.data.remote.datasource


import com.gmail.hejosadix.GetPeopleQuery
import com.gmail.hejosadix.GetPersonQuery
import com.gmail.hejosadix.starwars.data.common.StarWarsResult

interface PeopleRemoteDataSource {
    suspend fun getPeople(
        first: Int,
        after: String,
    ): StarWarsResult<GetPeopleQuery.AllPeople?>

    suspend fun getPerson(
        id: String,
    ): StarWarsResult<GetPersonQuery.Person?>
}
