package com.gmail.hejosadix.starwars.data.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.gmail.hejosadix.GetPeopleQuery
import com.gmail.hejosadix.starwars.data.common.RandomException
import com.gmail.hejosadix.starwars.data.common.ServerException
import com.gmail.hejosadix.starwars.data.mappers.mapToDomainModel
import com.gmail.hejosadix.starwars.domain.models.Person

class PeoplePagingSource(
    private val apolloClient: ApolloClient
) : PagingSource<String, Person>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, Person> {
        val pageIndex = params.key
        val first = params.loadSize
        try {
            val result = apolloClient.query(
                GetPeopleQuery(
                    first = Optional.presentIfNotNull(first),
                    after = Optional.presentIfNotNull(pageIndex),
                )
            ).execute()
            return if (result.hasErrors()) {
                LoadResult.Error(ServerException(result.errors?.first()?.message ?: ""))
            } else {
                val nextKey =
                    if (result.data?.allPeople?.pageInfo?.hasNextPage == true) {
                        result.data?.allPeople?.pageInfo?.endCursor
                    } else {
                        null
                    }
                LoadResult.Page(
                    data = result.data?.allPeople?.people?.map {
                        it?.mapToDomainModel() ?: Person()
                    } ?: emptyList(),
                    prevKey = pageIndex,
                    nextKey = nextKey,
                )
            }
        } catch (e: Exception) {
            return LoadResult.Error(RandomException(e.message.orEmpty()))
        }
    }

    override fun getRefreshKey(state: PagingState<String, Person>): String? {
        return null
    }
}