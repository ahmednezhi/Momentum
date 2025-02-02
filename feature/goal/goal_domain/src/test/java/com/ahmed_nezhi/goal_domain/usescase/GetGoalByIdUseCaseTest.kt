package com.ahmed_nezhi.goal_domain.usescase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmed_nezhi.common.enums.CategoryEnum
import com.ahmed_nezhi.common.enums.FrequencyEnum
import com.ahmed_nezhi.goal_domain.model.Goal
import com.ahmed_nezhi.goal_domain.repository.GoalRepository
import com.ahmed_nezhi.goal_domain.usecase.GetGoalByIdUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.junit.Assert.*
import org.junit.Rule
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
class GetGoalByIdUseCaseTest {

    // Rule to run the test with a Dispatcher that uses a TestCoroutineDispatcher

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    private var goalRepository: GoalRepository = mockk()

    private lateinit var getGoalByIdUseCase: GetGoalByIdUseCase

    @Before
    fun setup() {
        // Initialize GetGoalByIdUseCase with the mocked repository
        getGoalByIdUseCase = GetGoalByIdUseCase(goalRepository)
    }

    @Test
    fun `should return goal by id`() = runTest {
        // Arrange: Create a goal and mock repository
        val goalId = 1
        val mockGoal = Goal(
            id = goalId,
            name = "Test Goal",
            description = "Test Description",
            category = CategoryEnum.FITNESS,
            startDate = "2025-01-01",
            endDate = "2025-01-05",
            frequency = FrequencyEnum.DAILY
        )

        // Mock the repository to return the mock goal when getGoalById is called
        coEvery { goalRepository.getGoalById(goalId) } returns flowOf(mockGoal)

        // Act: Call the use case
        val result = getGoalByIdUseCase(goalId)

        // Assert: Verify that the repository method was called with the correct ID
        coVerify { goalRepository.getGoalById(goalId) }

        // Collect the flow and assert the goal returned is correct
        result.collect { goal ->
            assertEquals(mockGoal, goal)
        }
    }

}
