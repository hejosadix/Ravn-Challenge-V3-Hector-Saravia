package com.gmail.hejosadix.starwars.domain.models

data class PageInfo(
    val hasNextPage: Boolean = false,
    val hasPreviousPage: Boolean = false,
    val startCursor: String = "",
    val endCursor: String = "",
)
