package com.gmail.hejosadix.starwars.data.remote.datasource

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional

import com.gmail.hejosadix.GetPeopleQuery
import com.gmail.hejosadix.GetPersonQuery
import com.gmail.hejosadix.starwars.data.common.RandomException
import com.gmail.hejosadix.starwars.data.common.ServerException
import com.gmail.hejosadix.starwars.data.common.StarWarsResult

class PeopleRemoteDataSourceImpl(
    private val apolloClient: ApolloClient,
) : PeopleRemoteDataSource {
    override suspend fun getPeople(
        first: Int,
        after: String,
    ): StarWarsResult<GetPeopleQuery.AllPeople?> {
        return try {
            val result = apolloClient.query(
                GetPeopleQuery(
                    first = Optional.presentIfNotNull(first),
                    after = Optional.presentIfNotNull(after),
                )
            ).execute()
            if (result.hasErrors()) {
                StarWarsResult.Error(ServerException(result.errors?.first()?.message ?: ""))
            } else {
                StarWarsResult.Success(result.data?.allPeople)
            }
        } catch (e: Exception) {
            StarWarsResult.Error(RandomException(e.message.orEmpty()))
        }
    }

    override suspend fun getPerson(
        id: String,
    ): StarWarsResult<GetPersonQuery.Person?> {
        return try {
            val result = apolloClient.query(
                GetPersonQuery(id = Optional.presentIfNotNull(id)),
            ).execute()
            if (result.hasErrors()) {
                StarWarsResult.Error(ServerException(result.errors?.first()?.message ?: ""))
            } else {
                StarWarsResult.Success(result.data?.person)
            }
        } catch (e: Exception) {
            StarWarsResult.Error(RandomException(e.message.orEmpty()))
        }
    }
}
