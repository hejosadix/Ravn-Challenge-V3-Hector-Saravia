package com.gmail.hejosadix.starwars.domain.models

data class Species(
    val name: String = "",
    val homeWorld: HomeWorld = HomeWorld(),
)