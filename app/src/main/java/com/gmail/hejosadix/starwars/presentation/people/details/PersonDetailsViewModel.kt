package com.gmail.hejosadix.starwars.presentation.people.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.hejosadix.starwars.domain.models.Person
import com.gmail.hejosadix.starwars.domain.usecases.people.PersonUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonDetailsViewModel(private val personUseCases: PersonUseCases) : ViewModel() {

    suspend fun getPerson(id: String) = personUseCases.getPersonUseCase.invoke(id = id)
    fun getFavorite(id: String) = personUseCases.getFavoriteUseCase.invoke(id = id)
    fun savePersonInFavorite(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            personUseCases.saveFavoriteUseCase(person = person)
        }
    }

    fun deletePersonInFavorite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            personUseCases.deleteFavoriteUseCase(id = id)
        }
    }
}