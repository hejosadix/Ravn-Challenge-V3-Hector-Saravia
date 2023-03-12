package com.gmail.hejosadix.starwars.data.mappers

import com.gmail.hejosadix.GetPeopleQuery
import com.gmail.hejosadix.GetPersonQuery
import com.gmail.hejosadix.starwars.data.local.entity.Favorite
import com.gmail.hejosadix.starwars.domain.models.*


fun GetPeopleQuery.PageInfo.mapToDomainModel() = PageInfo(
    hasNextPage = hasNextPage,
    hasPreviousPage = hasPreviousPage,
    startCursor = startCursor.orEmpty(),
    endCursor = endCursor.orEmpty(),
)

fun GetPeopleQuery.Species.mapToDomainModel() = Species(
    name = name.orEmpty(),
    homeWorld = homeworld?.mapToDomainModel() ?: HomeWorld(),
)

fun GetPeopleQuery.Homeworld.mapToDomainModel() = HomeWorld(
    name = name.orEmpty(),
)

fun GetPeopleQuery.Person.mapToDomainModel() = Person(
    id = id,
    name = name.orEmpty(),
    eyeColor = eyeColor.orEmpty(),
    hairColor = hairColor.orEmpty(),
    species = species?.mapToDomainModel() ?: Species(),
)

fun GetPeopleQuery.AllPeople.mapToDomainModel() = StarWarsPeople(
    pageInfo = pageInfo.mapToDomainModel(),
    people = people?.map {
        it?.mapToDomainModel() ?: Person()
    } ?: emptyList(),
)

fun GetPersonQuery.Person.mapToDomainModel() = Person(
    id = id,
    name = name.orEmpty(),
    eyeColor = eyeColor.orEmpty(),
    hairColor = hairColor.orEmpty(),
    species = species?.mapToDomainModel() ?: Species(),
    vehicleConnection = vehicleConnection?.mapToDomainModel() ?: VehicleConnection(),
)

fun GetPersonQuery.Species.mapToDomainModel() = Species(
    name = name.orEmpty(),
    homeWorld = homeworld?.mapToDomainModel() ?: HomeWorld(),
)

fun GetPersonQuery.Homeworld.mapToDomainModel() = HomeWorld(
    name = name.orEmpty(),
)

fun GetPersonQuery.VehicleConnection.mapToDomainModel() = VehicleConnection(
    vehicles = vehicles?.map {
        it?.mapToDomainModel() ?: Vehicle()
    } ?: emptyList(),
)

fun GetPersonQuery.Vehicle.mapToDomainModel() = Vehicle(
    id = id,
    name = name.orEmpty(),
    model = model.orEmpty(),
)

fun Person.toFavorite() = Favorite(
    id = this.id,
    name = name,
    eyeColor = eyeColor,
    hairColor = hairColor,
    skinColor = skinColor,
    birthYear = birthYear,
    species = species,
    vehicleConnection = vehicleConnection,
)