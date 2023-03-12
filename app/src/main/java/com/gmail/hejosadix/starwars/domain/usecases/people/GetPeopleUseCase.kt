package com.gmail.hejosadix.starwars.domain.usecases.people

import com.gmail.hejosadix.starwars.domain.repository.PeopleRepository

class GetPeopleUseCase constructor(
    private val repository: PeopleRepository,
) {
    suspend operator fun invoke(
        first: Int,
        after: String,
    ) = repository.getPeople(
        first = first, after = after,
    )
}