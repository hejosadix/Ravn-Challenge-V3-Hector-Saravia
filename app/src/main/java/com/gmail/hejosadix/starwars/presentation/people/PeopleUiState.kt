package com.gmail.hejosadix.starwars.presentation.people


sealed class PeopleUiState<out T> {
    object None : PeopleUiState<Nothing>()
    object Loading : PeopleUiState<Nothing>()
    data class Add<out T>(
        val data: T,
    ) : PeopleUiState<T>()

    data class Error(
        val message: String,
    ) : PeopleUiState<Nothing>()

    object Executed : PeopleUiState<Nothing>()
}
