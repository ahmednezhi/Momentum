package com.ahmed_nezhi.home_presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmed_nezhi.common.enums.CategoryEnum
import com.ahmed_nezhi.common.enums.FrequencyEnum
import com.ahmed_nezhi.goal_domain.model.Goal
import com.ahmed_nezhi.goal_domain.usecase.GetAllGoalsUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @MockK
    lateinit var getAllGoalsUseCase: GetAllGoalsUseCase

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)  // Initialize mocks
        viewModel = HomeViewModel(getAllGoalsUseCase)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchGoals should update uiState with loading state when before fetching`() = runTest {
        // Arrange
        val selectedDate = "2023-10-01"
        val goals = listOf(
            Goal(
                id = 1,
                name = "Goal 1",
                description = "Description 1",
                category = CategoryEnum.HEALTH,
                startDate = "2023-10-01",
                endDate = "2023-10-10",
                frequency = FrequencyEnum.DAILY
            )
        )

        // Mock the use case to return goals
        coEvery { getAllGoalsUseCase(selectedDate) } returns flow { emit(goals) }

        // Act
        val uiStateList = mutableListOf<HomeViewModel.HomeUiState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.toList(uiStateList) // Collect all emitted states
        }

        // Emit selected date to trigger fetching
        viewModel.updateSelectedDate(selectedDate)

        // Assert
        // Assert the number of items collected (it should be at least 2: initial state and updated state)
        assertEquals(1, uiStateList.size)

        assertEquals(
            HomeViewModel.HomeUiState(isLoading = true),
            uiStateList[0]
        )
    }

    @Test
    fun `fetchGoals should update uiState with goals when successful`() = runTest {
        // Arrange
        val selectedDate = "2023-10-01"
        val goals = listOf(
            Goal(
                id = 1,
                name = "Goal 1",
                description = "Description 1",
                category = CategoryEnum.HEALTH,
                startDate = "2023-10-01",
                endDate = "2023-10-10",
                frequency = FrequencyEnum.DAILY
            ),
            Goal(
                id = 2,
                name = "Goal 2",
                description = "Description 2",
                category = CategoryEnum.FITNESS,
                startDate = "2023-10-01",
                endDate = "2023-10-15",
                frequency = FrequencyEnum.DAILY
            )
        )

        // Mock the use case to return the list of goals
        coEvery { getAllGoalsUseCase(selectedDate) } returns flow { emit(goals) }

        // Act
        val uiStateList = mutableListOf<HomeViewModel.HomeUiState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.toList(uiStateList)
        }

        // Emit selected date to trigger fetching
        viewModel.updateSelectedDate(selectedDate)

        // Ensure all states are collected (advance the dispatcher to ensure flow completion)
        advanceUntilIdle()

        // Assert
        // Assert the number of items collected (it should be at least 2: initial loading state and updated state)
        assertEquals(2, uiStateList.size)

        // Assert the updated uiState with the list of goals
        assertEquals(
            HomeViewModel.HomeUiState(goals = goals, isLoading = false),
            uiStateList[1]
        )
    }


}