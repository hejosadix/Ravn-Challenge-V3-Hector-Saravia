package com.gmail.hejosadix.starwars.data.remote.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional

import com.gmail.hejosadix.GetPersonQuery
import com.gmail.hejosadix.starwars.data.common.RandomException
import com.gmail.hejosadix.starwars.data.common.ServerException
import com.gmail.hejosadix.starwars.data.common.StarWarsResult
import com.gmail.hejosadix.starwars.domain.models.Person
import kotlinx.coroutines.flow.Flow

class PeopleRemoteDataSourceImpl(
    private val apolloClient: ApolloClient,
) : PeopleRemoteDataSource {
    override fun getPeople(
    ): Flow<PagingData<Person>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false,
                initialLoadSize = 5,
            ),
            pagingSourceFactory = {
                PeoplePagingSource(apolloClient = apolloClient)
            }
        ).flow
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
