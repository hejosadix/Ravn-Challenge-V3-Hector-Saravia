package com.gmail.hejosadix.starwars.domain.usecases.people

import com.gmail.hejosadix.starwars.domain.repository.PeopleRepository

class GetPeopleUseCase constructor(
    private val repository: PeopleRepository,
) {
    operator fun invoke(
    ) = repository.getPeople(
    )
}