package com.gmail.hejosadix.starwars.presentation.people

import com.gmail.hejosadix.starwars.data.common.StarWarsResult
import com.gmail.hejosadix.starwars.domain.models.PageInfo
import com.gmail.hejosadix.starwars.domain.models.StarWarsPeople
import com.gmail.hejosadix.starwars.domain.models.Person
import com.gmail.hejosadix.starwars.domain.usecases.people.GetPeopleUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class StarWarsPeopleViewModelTest {

    private lateinit var viewModel: StarWarsPeopleViewModel
    private lateinit var getPeopleUseCase: GetPeopleUseCase

    private val fakePeople = listOf(
        Person("tyer7sadf564", "Luke Skywalker"), Person("tyer7t7rty9", "Darth Vader")
    )

    private val fakePageInfo = PageInfo(
        endCursor = "", hasNextPage = false
    )

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        getPeopleUseCase = mockk()
        viewModel = StarWarsPeopleViewModel(getPeopleUseCase)
        Dispatchers.setMain(dispatcher = Dispatchers.IO)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `when getAllPeople is called and use case returns success then ui state is executed`() =
        runBlocking {
            var job: Job? = null
            job = launch {
                // given
                coEvery { getPeopleUseCase.invoke(any(), any()) } returns flowOf(
                    StarWarsResult.Success(
                        data = StarWarsPeople(
                            people = fakePeople, pageInfo = fakePageInfo
                        )
                    )
                )
                // went
                viewModel.getAllPeople()
                //then
                viewModel.peopleUiState.collectLatest {
                    assert(it is PeopleUiState.Executed)
                    job?.cancel()
                }

            }
        }
}