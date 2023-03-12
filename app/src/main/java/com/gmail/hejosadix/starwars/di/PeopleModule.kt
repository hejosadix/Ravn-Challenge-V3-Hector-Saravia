package com.gmail.hejosadix.starwars.di

import com.gmail.hejosadix.starwars.data.remote.datasource.PeopleRemoteDataSource
import com.gmail.hejosadix.starwars.data.remote.datasource.PeopleRemoteDataSourceImpl
import com.gmail.hejosadix.starwars.data.repository.PeopleRepositoryImpl
import com.gmail.hejosadix.starwars.domain.repository.PeopleRepository
import com.gmail.hejosadix.starwars.domain.usecases.people.GetPeopleUseCase
import com.gmail.hejosadix.starwars.domain.usecases.people.GetPersonUseCase
import org.koin.dsl.module


val peopleModule = module {
    single<PeopleRemoteDataSource> {
        PeopleRemoteDataSourceImpl(
            apolloClient = get(),
        )
    }
    single<PeopleRepository> {
        PeopleRepositoryImpl(
            remoteDataSource = get(),
        )
    }
    factory {
        GetPeopleUseCase(
            repository = get(),
        )
    }
    factory {
        GetPersonUseCase(
            repository = get(),
        )
    }
}


