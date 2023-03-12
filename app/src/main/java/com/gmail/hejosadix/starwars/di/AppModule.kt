package com.gmail.hejosadix.starwars.di

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.gmail.hejosadix.starwars.data.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single {
        apolloClient()
    }
    single {
        provideAppDatabase(
            androidContext(),
        )
    }
}

private fun apolloClient(): ApolloClient =
    ApolloClient.Builder()
        .serverUrl("https://swapi-graphql.netlify.app/.netlify/functions/index").build()


fun provideAppDatabase(context: Context) = AppDatabase.getDatabase(context)

