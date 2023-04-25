package com.gmail.hejosadix.starwars.presentation.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import com.gmail.hejosadix.starwars.domain.models.Person
import com.gmail.hejosadix.starwars.domain.usecases.people.GetPeopleUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class StarWarsPeopleViewModel(getPeopleUseCase: GetPeopleUseCase) : ViewModel() {
    private val _peopleUiState =
        MutableStateFlow<PeopleUiState<List<Person>>>(PeopleUiState.None)
    val peopleUiState: StateFlow<PeopleUiState<List<Person>>> = _peopleUiState
    private val querySearch: MutableStateFlow<String> = MutableStateFlow("")
    private val people = getPeopleUseCase.invoke().cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAllPeople() = querySearch.flatMapLatest { queryString ->
        people.map {
            it.filter { person ->
                person.name.contains(queryString, ignoreCase = true)
            }
        }
    }

    fun searchPeople(
        text: String
    ) {
        querySearch.value = text
    }

}