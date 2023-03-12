package com.gmail.hejosadix.starwars.data.common

sealed class StarWarsResult<out T> {
    object Loading : StarWarsResult<Nothing>()
    data class Success<out T>(val data: T) : StarWarsResult<T>()
    data class Error(val exception: Exception) : StarWarsResult<Nothing>()
}

