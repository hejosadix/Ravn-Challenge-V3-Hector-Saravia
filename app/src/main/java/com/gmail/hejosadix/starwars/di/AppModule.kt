package com.gmail.hejosadix.starwars.di

import com.apollographql.apollo3.ApolloClient
import org.koin.dsl.module

val appModule = module {
    single {
        apolloClient()
    }
}
private fun apolloClient(): ApolloClient =
    ApolloClient.Builder()
        .serverUrl("https://swapi-graphql.netlify.app/.netlify/functions/index").build()
