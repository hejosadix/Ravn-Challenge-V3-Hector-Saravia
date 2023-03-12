package com.gmail.hejosadix.starwars.presentation.people.details

import com.gmail.hejosadix.starwars.data.local.entity.Favorite
import com.gmail.hejosadix.starwars.domain.models.Person
import com.gmail.hejosadix.starwars.domain.usecases.people.PersonUseCases
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class PersonDetailsViewModelTest {
    private lateinit var viewModel: PersonDetailsViewModel
    private val mockPersonUseCases: PersonUseCases = mockk()
    private val fakeFavorite =
        Favorite("tyer7sadf564", "Luke Skywalker")


    @Before
    fun setUp() {
        viewModel = PersonDetailsViewModel(mockPersonUseCases)
    }

    @Test
    fun `test getFavorite()`() = runBlocking {
        coEvery { mockPersonUseCases.getFavoriteUseCase.invoke(any()) } returns flowOf(
            fakeFavorite
        )
        val result = viewModel.getFavorite(fakeFavorite.id)
        result.collectLatest {
            assert(fakeFavorite == it)
        }
    }


    @Test
    fun `test savePersonInFavorite()`() = runBlocking {
        val person = Person("tyer7sadf564", "Luke Skywalker")

        coEvery { mockPersonUseCases.getFavoriteUseCase.invoke(any()) } returns flowOf(
            fakeFavorite
        )
        viewModel.savePersonInFavorite(person)

        val result = viewModel.getFavorite(person.id)
        result.collectLatest {
            assert(fakeFavorite == it)
        }
    }

}