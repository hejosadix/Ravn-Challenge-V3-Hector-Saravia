package com.gmail.hejosadix.starwars.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.hejosadix.starwars.domain.models.Species
import com.gmail.hejosadix.starwars.domain.models.VehicleConnection

@Entity
data class Favorite(
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    val name: String = "",
    val eyeColor: String = "",
    val hairColor: String = "",
    val skinColor: String = "",
    val birthYear: String = "",
    val species: Species = Species(),
    val vehicleConnection: VehicleConnection = VehicleConnection(),
)
