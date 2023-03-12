package com.gmail.hejosadix.starwars.domain.models

data class Person(
    val id: String = "",
    val name: String = "",
    val eyeColor: String = "",
    val hairColor: String = "",
    val skinColor: String = "",
    val birthYear: String = "",
    val species: Species = Species(),
    val vehicleConnection: VehicleConnection = VehicleConnection(),
)



