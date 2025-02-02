package com.ahmed_nezhi.goal_presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmed_nezhi.common.enums.GoalStatus
import com.ahmed_nezhi.goal_domain.model.Goal
import com.ahmed_nezhi.goal_domain.model.GoalProgress
import com.ahmed_nezhi.goal_domain.usecase.GetGoalByIdUseCase
import com.ahmed_nezhi.goal_domain.usecase.GetGoalProgressUseCase
import com.ahmed_nezhi.goal_domain.usecase.InsertGoalProgressUseCase
import com.ahmed_nezhi.goal_presentation.details.viewmodel.GoalDetailsViewModel
import com.ahmed_nezhi.ui.timeline.models.TimeLineData
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GoalDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: GoalDetailsViewModel

    private val getGoalByIdUseCase: GetGoalByIdUseCase = mockk()
    private val getGoalProgressUseCase: GetGoalProgressUseCase = mockk()
    private val addGoalProgressUseCase: InsertGoalProgressUseCase = mockk()

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = GoalDetailsViewModel(
            getGoalByIdUseCase = getGoalByIdUseCase,
            getGoalProgressUseCase = getGoalProgressUseCase,
            addGoalProgressUseCase = addGoalProgressUseCase
        )
    }

    @Test
    fun `loadGoal should load goal successfully`() = runTest {
        // Arrange: Mock the GetGoalByIdUseCase to return a goal
        val goal = Goal(
            id = 1,
            name = "Test Goal",
            description = "Test Description",
            category = mockk(),
            startDate = "2023-01-01",
            endDate = "2023-01-10",
            frequency = mockk()
        )
        coEvery { getGoalByIdUseCase(any()) } returns flowOf(goal)

        // Act: Load the goal
        viewModel.loadGoal(1)

        // Assert: Verify UI state updates with the goal data
        val uiState = viewModel.uiState.value
        assertEquals(goal, uiState.goal)
        assertFalse(uiState.isLoading)
        assertNull(uiState.errorMessage)
    }

    @Test
    fun `loadGoalProgress should load progress successfully`() = runTest {
        // Arrange: Mock the GetGoalProgressUseCase to return a progress list
        val goalProgress = GoalProgress(
            goalId = 1,
            description = "Test Progress",
            photoUrl = "test_url",
            date = "2023-01-01",
            status = GoalStatus.IN_PROGRESS
        )
        val timeLineData = listOf(
            TimeLineData(
                "",
                goalProgress.description,
                goalProgress.photoUrl,
                goalProgress.date,
                goalProgress.status
            )
        )
        coEvery { getGoalProgressUseCase(any()) } returns flowOf(listOf(goalProgress))

        // Act: Load goal progress
        viewModel.loadGoalProgress(1)

        // Assert: Verify UI state updates with the progress data
        val uiState = viewModel.uiState.value
        assertEquals(timeLineData, uiState.progress)
        assertFalse(uiState.isLoading)
        assertNull(uiState.errorMessage)
    }

}
