package com.gmail.hejosadix.starwars.di

import com.gmail.hejosadix.starwars.presentation.people.StarWarsPeopleViewModel
import com.gmail.hejosadix.starwars.presentation.people.details.PersonDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModel = module {
    viewModel {
        StarWarsPeopleViewModel(
            get(),
        )
    }
    viewModel { PersonDetailsViewModel(
        get(),
    ) }
}

