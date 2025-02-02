package com.ahmed_nezhi.goal_domain.usescase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmed_nezhi.common.enums.GoalStatus
import com.ahmed_nezhi.goal_domain.model.GoalProgress
import com.ahmed_nezhi.goal_domain.repository.GoalProgressRepository
import com.ahmed_nezhi.goal_domain.usecase.GetGoalProgressUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetGoalProgressUseCaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var goalProgressRepository: GoalProgressRepository
    private lateinit var getGoalProgressUseCase: GetGoalProgressUseCase

    @Before
    fun setup() {
        // Initialize the repository mock
        goalProgressRepository = mockk()
        // Initialize the use case with the mocked repository
        getGoalProgressUseCase = GetGoalProgressUseCase(goalProgressRepository)
    }

    @Test
    fun `should return goal progress list by goal id`() = runTest {
        // Arrange: Create a goal progress list and mock repository
        val goalId = 1
        val mockGoalProgressList = listOf(
            GoalProgress(
                goalId = goalId,
                photoUrl = "http://example.com/photo.jpg",
                date = "2025-01-01",
                description = "Test progress 1",
                status = GoalStatus.IN_PROGRESS
            ),
            GoalProgress(
                goalId = goalId,
                photoUrl = "http://example.com/photo2.jpg",
                date = "2025-01-02",
                description = "Test progress 2",
                status = GoalStatus.COMPLETED
            )
        )

        // Mock the repository to return the mock progress list
        coEvery { goalProgressRepository.getGoalProgress(goalId) } returns flowOf(mockGoalProgressList)

        // Act: Call the use case
        val result = getGoalProgressUseCase(goalId)

        // Assert: Verify that the repository method was called with the correct ID
        coVerify { goalProgressRepository.getGoalProgress(goalId) }

        // Collect the flow and assert the result
        result.collect { progressList ->
            assertEquals(mockGoalProgressList, progressList)
        }
    }
}
