package com.gmail.hejosadix.starwars.presentation.people

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.gmail.hejosadix.starwars.domain.models.Person
import com.gmail.hejosadix.starwars.domain.usecases.people.GetPeopleUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
class StarWarsPeopleViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var viewModel: StarWarsPeopleViewModel
    private val getPeopleUseCase = mockk<GetPeopleUseCase>(relaxed = true)

    @Before
    fun setup() {
        viewModel = StarWarsPeopleViewModel(getPeopleUseCase)
        viewModel.getAllPeople()
    }

    @Test
    fun `test getAllPeople with empty search query`() = testScope.runBlockingTest {
        // Given
        val people = listOf(
            Person("1", "Luke Skywalker"),
            Person("2", "Leia Organa"),
            Person("3", "Han Solo"),
            Person("4", "Chewbacca")
        )
        val expectedPagingData = PagingData.from(people)
        coEvery { getPeopleUseCase.invoke() } returns flowOf(expectedPagingData)

        // When
        val result = viewModel.getAllPeople().first()

        // Then
        assertEquals(expectedPagingData, result)
    }

    @Test
    fun `test searchPeople with non-empty search query`() {
        // Given
        val query = "luke"
        val expectedQuerySearch = MutableStateFlow(query)

        // When
        viewModel.searchPeople(query)

        // Then
        assertEquals(expectedQuerySearch.value, viewModel.querySearch.value)
    }

    @Test
    fun `test getAllPeople with non-empty search query`() = testScope.runBlockingTest {
        // Given
        val people = listOf(
            Person("1", "Luke Skywalker"),
            Person("2", "Leia Organa"),
            Person("3", "Han Solo"),
            Person("4", "Chewbacca")
        )
        val expectedFilteredPeople = PagingData.from(listOf(
            Person("1", "Luke Skywalker")
        ))
        val expectedPagingData = PagingData.from(people)
        coEvery { getPeopleUseCase.invoke() } returns flowOf(expectedPagingData)

        // When
        viewModel.searchPeople("luke")
        val result = viewModel.getAllPeople().toList()

        // Then
        assertEquals(listOf(expectedFilteredPeople), result)
    }
}