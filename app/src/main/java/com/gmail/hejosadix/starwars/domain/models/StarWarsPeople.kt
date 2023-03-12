package com.gmail.hejosadix.starwars.domain.models

data class StarWarsPeople(
    val pageInfo: PageInfo,
    val people: List<Person>,
)
