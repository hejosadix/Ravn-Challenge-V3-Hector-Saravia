package com.gmail.hejosadix.starwars.presentation.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.hejosadix.starwars.data.common.StarWarsResult
import com.gmail.hejosadix.starwars.domain.models.PageInfo
import com.gmail.hejosadix.starwars.domain.models.Person
import com.gmail.hejosadix.starwars.domain.usecases.people.GetPeopleUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

import kotlinx.coroutines.launch

class StarWarsPeopleViewModel(private val getPeopleUseCase: GetPeopleUseCase) : ViewModel() {
    private val _peopleUiState =
        MutableStateFlow<PeopleUiState<List<Person>>>(PeopleUiState.None)
    val peopleUiState: StateFlow<PeopleUiState<List<Person>>> = _peopleUiState
    private var job: Job? = null
    fun getAllPeople(first: Int = 5, after: String = "") {
        job?.cancel()
        job = viewModelScope.launch {
            getPeopleUseCase.invoke(first = first, after = after).collectLatest {
                when (it) {
                    is StarWarsResult.Success -> {
                        _peopleUiState.value = PeopleUiState.Add(
                            data = it.data.people,
                        )
                        hasNextPage(pageInfo = it.data.pageInfo)
                    }
                    is StarWarsResult.Error -> {
                        _peopleUiState.value =
                            PeopleUiState.Error(it.exception.message.orEmpty())
                    }
                    is StarWarsResult.Loading -> {

                        _peopleUiState.value = PeopleUiState.Loading
                    }
                }

            }
        }
    }

    private fun hasNextPage(pageInfo: PageInfo) {
        if (pageInfo.hasNextPage) {
            getAllPeople(after = pageInfo.endCursor)
        } else {
            _peopleUiState.value = PeopleUiState.Executed
        }
    }

}