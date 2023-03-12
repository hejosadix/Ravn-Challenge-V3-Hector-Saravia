package com.gmail.hejosadix.starwars.di

import com.gmail.hejosadix.starwars.data.local.AppDatabase
import com.gmail.hejosadix.starwars.data.repository.FavoriteRepositoryImpl
import com.gmail.hejosadix.starwars.domain.repository.FavoriteRepository
import com.gmail.hejosadix.starwars.domain.usecases.favorite.DeleteFavoriteUseCase
import com.gmail.hejosadix.starwars.domain.usecases.favorite.GetFavoriteUseCase
import com.gmail.hejosadix.starwars.domain.usecases.favorite.SaveFavoriteUseCase
import org.koin.dsl.module

val favoriteModule = module {
    single {
        provideFavoriteDao(
            get(),
        )
    }
    single<FavoriteRepository> {
        FavoriteRepositoryImpl(
            local = get(),
        )
    }
    factory {
        DeleteFavoriteUseCase(
            repository = get(),
        )
    }
    factory {
        SaveFavoriteUseCase(
            repository = get(),
        )
    }
    factory {
        GetFavoriteUseCase(
            repository = get(),
        )
    }
}

fun provideFavoriteDao(db: AppDatabase) = db.favoriteDao()


