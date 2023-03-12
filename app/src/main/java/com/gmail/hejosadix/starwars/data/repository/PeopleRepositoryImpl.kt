package com.gmail.hejosadix.starwars.data.repository

import com.gmail.hejosadix.starwars.data.common.StarWarsResult
import com.gmail.hejosadix.starwars.data.mappers.mapToDomainModel
import com.gmail.hejosadix.starwars.data.remote.datasource.PeopleRemoteDataSource
import com.gmail.hejosadix.starwars.domain.models.*
import com.gmail.hejosadix.starwars.domain.repository.PeopleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class PeopleRepositoryImpl(
    private val remoteDataSource: PeopleRemoteDataSource,
) : PeopleRepository {
    override suspend fun getPeople(
        first: Int, after: String,
    ): Flow<StarWarsResult<StarWarsPeople>> =
        flow {
            when (val result = remoteDataSource.getPeople(first = first, after = after)) {
                is StarWarsResult.Success -> {
                    result.data?.let {
                        it.mapToDomainModel().apply {
                            emit(StarWarsResult.Success(this))
                        }
                    }
                }
                is StarWarsResult.Error -> {
                    emit(StarWarsResult.Error(result.exception))
                }
                StarWarsResult.Loading -> {

                }
            }
        }.onStart { emit(StarWarsResult.Loading) }

    override suspend fun getPerson(
        id: String,
    ): Flow<StarWarsResult<Person>> =
        flow {
            when (val result = remoteDataSource.getPerson(
                id = id,
            )) {
                is StarWarsResult.Success -> {
                    result.data?.let {
                        it.mapToDomainModel().apply {
                            emit(StarWarsResult.Success(this))
                        }
                    }
                }
                is StarWarsResult.Error -> {
                    emit(StarWarsResult.Error(result.exception))
                }
                StarWarsResult.Loading -> {

                }
            }
        }.onStart { emit(StarWarsResult.Loading) }
}
