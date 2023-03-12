package com.gmail.hejosadix.starwars.domain.models

data class VehicleConnection(
    val vehicles: List<Vehicle> = emptyList(),
)

data class Vehicle(
    val id: String = "",
    val name: String = "",
    val model: String = "",
)