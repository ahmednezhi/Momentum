package com.ahmed_nezhi.goal_presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmed_nezhi.goal_domain.usecase.InsertGoalUseCase
import com.ahmed_nezhi.goal_domain.usecase.ScheduleNotificationsUseCase
import com.ahmed_nezhi.goal_presentation.add.viewmodel.AddGoalViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddGoalViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: AddGoalViewModel

    private val insertGoalUseCase: InsertGoalUseCase = mockk()
    private val scheduleNotificationsUseCase: ScheduleNotificationsUseCase = mockk()

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = AddGoalViewModel(
            insertGoalUseCase = insertGoalUseCase,
            scheduleNotificationsUseCase = scheduleNotificationsUseCase
        )
    }

    @Test
    fun `submitGoal should not submit when form is invalid`() = runTest {
        // Arrange: Invalid goal state (empty name)
        val invalidGoalState = AddGoalViewModel.UiState(
            name = "",
            description = "Test Description",
            category = "HEALTH",
            startDate = "2023-10-01",
            endDate = "2023-10-10",
            frequency = "Each Day",
            isValid = false
        )

        // Act: Try to submit with invalid state
        viewModel.onEvent(AddGoalViewModel.FormEvent.NameChanged(""))
        viewModel.onEvent(AddGoalViewModel.FormEvent.DescriptionChanged("Test Description"))
        viewModel.onEvent(AddGoalViewModel.FormEvent.CategoryChanged("HEALTH"))
        viewModel.onEvent(AddGoalViewModel.FormEvent.DateRangeChanged("2023-10-01", "2023-10-10"))
        viewModel.onEvent(AddGoalViewModel.FormEvent.FrequencyChanged("Each Day"))
        viewModel.onEvent(AddGoalViewModel.FormEvent.SubmitGoal)

        // Assert: Verify goal is not inserted, no notifications are scheduled
        coVerify(exactly = 0) { insertGoalUseCase(any()) }
        coVerify(exactly = 0) { scheduleNotificationsUseCase(any()) }

        // Assert UI state remains invalid
        val uiState = viewModel.uiState.first()
        assertEquals(invalidGoalState.copy(isValid = false), uiState)
    }

    @Test
    fun `toggleCategorySheet should toggle category sheet visibility`() = runTest {
        // Arrange: Initial state with category sheet hidden
        val initialState = AddGoalViewModel.UiState(showCategorySheet = false)

        // Act: Toggle category sheet
        viewModel.onEvent(AddGoalViewModel.FormEvent.ToggleCategorySheet)
        val updatedState = viewModel.uiState.first()

        // Assert: Visibility state is toggled
        assertNotEquals(initialState.showCategorySheet, updatedState.showCategorySheet)
    }

    @Test
    fun `toggleDateRangePicker should toggle date range picker visibility`() = runTest {
        // Arrange: Initial state with date range picker hidden
        val initialState = AddGoalViewModel.UiState(showDateRangePicker = false)

        // Act: Toggle date range picker
        viewModel.onEvent(AddGoalViewModel.FormEvent.ToggleDateRangePicker)
        val updatedState = viewModel.uiState.first()

        // Assert: Visibility state is toggled
        assertNotEquals(initialState.showDateRangePicker, updatedState.showDateRangePicker)
    }

}
