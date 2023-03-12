package com.gmail.hejosadix.starwars.presentation.people.details

import androidx.lifecycle.ViewModel
import com.gmail.hejosadix.starwars.domain.usecases.people.GetPersonUseCase

class PersonDetailsViewModel(private val getPersonUseCase: GetPersonUseCase) : ViewModel() {

    suspend fun getPerson(id: String) = getPersonUseCase.invoke(id = id)
}