package com.gmail.hejosadix.starwars.domain.usecases.people

import com.gmail.hejosadix.starwars.domain.repository.PeopleRepository

class GetPersonUseCase constructor(
    private val repository: PeopleRepository,
) {
    suspend operator fun invoke(
        id: String,
    ) = repository.getPerson(
        id = id,
    )
}