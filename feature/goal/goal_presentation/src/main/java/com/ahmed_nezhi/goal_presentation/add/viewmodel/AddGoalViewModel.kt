package com.ahmed_nezhi.goal_presentation.add.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed_nezhi.common.enums.CategoryEnum
import com.ahmed_nezhi.common.enums.FrequencyEnum
import com.ahmed_nezhi.goal_domain.model.Goal
import com.ahmed_nezhi.goal_domain.usecase.InsertGoalUseCase
import com.ahmed_nezhi.goal_domain.usecase.ScheduleNotificationsUseCase
import com.ahmed_nezhi.notifications.Alarm
import com.ahmed_nezhi.notifications.AlarmScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Random
import javax.inject.Inject

@HiltViewModel
class AddGoalViewModel @Inject constructor(
    private val insertGoalUseCase: InsertGoalUseCase,
    private val scheduleNotificationsUseCase: ScheduleNotificationsUseCase
) : ViewModel() {

    val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> get() = _uiState

    fun onEvent(event: FormEvent) {
        when (event) {
            is FormEvent.NameChanged -> updateName(event.name)
            is FormEvent.DescriptionChanged -> updateDescription(event.description)
            is FormEvent.CategoryChanged -> updateCategory(event.category)
            is FormEvent.DateRangeChanged -> updateDateRange(event.startDate, event.endDate)
            is FormEvent.FrequencyChanged -> updateFrequency(event.frequency)
            is FormEvent.SubmitGoal -> submitGoal()
            is FormEvent.ToggleCategorySheet -> toggleCategorySheet()
            is FormEvent.ToggleDateRangePicker -> toggleDateRangePicker()
        }
    }

    // Update name field and validation
    private fun updateName(name: String) {
        val updatedState = _uiState.value.copy(name = name)
        updateUiState(updatedState)
    }

    // Update description field and validation
    private fun updateDescription(description: String) {
        val updatedState = _uiState.value.copy(description = description)
        updateUiState(updatedState)
    }

    // Update category field and validation
    private fun updateCategory(category: String) {
        val updatedState = _uiState.value.copy(category = category)
        updateUiState(updatedState)
    }

    // Update start and end date field and validation
    private fun updateDateRange(startDate: String, endDate: String) {
        val updatedState = _uiState.value.copy(
            startDate = startDate,
            endDate = endDate
        )
        updateUiState(updatedState)
    }

    // Update frequency field and validation
    private fun updateFrequency(frequency: String) {
        val updatedState = _uiState.value.copy(frequency = frequency)
        updateUiState(updatedState)
    }

    // Show or hide category sheet
    private fun toggleCategorySheet() {
        val updatedState =
            _uiState.value.copy(showCategorySheet = !_uiState.value.showCategorySheet)
        updateUiState(updatedState)
    }

    // Show or hide date range picker
    private fun toggleDateRangePicker() {
        val updatedState =
            _uiState.value.copy(showDateRangePicker = !_uiState.value.showDateRangePicker)
        updateUiState(updatedState)
    }

    private fun submitGoal() {
        val currentState = _uiState.value
        if (currentState.isValid) {
            viewModelScope.launch {
                val goal = Goal(
                    name = currentState.name,
                    description = currentState.description,
                    category = CategoryEnum.valueOf(currentState.category),
                    startDate = currentState.startDate.format(dateFormatter),
                    endDate = currentState.endDate.format(dateFormatter),
                    frequency = FrequencyEnum.valueOf(currentState.frequency),
                )
                insertGoalUseCase(goal)
                // create Notifications/Alarms for each day for the goal
                // Currently the time of notification is Randomly set between 7 & 8pm every day between the start and the end date
                // following the frequency of the goal (daily, each two days..)
                withContext(Dispatchers.Main){
                    scheduleNotificationsUseCase(goal)
                }
            }
        }
    }



    private fun updateUiState(newState: UiState) {
        _uiState.value = newState.copy(isValid = validateForm(newState))
    }

    private fun validateForm(state: UiState): Boolean {
        return state.name.isNotBlank() &&
                state.description.isNotBlank() &&
                state.category != "Select Category"
    }

    /**
     * Data class representing the UI state of the form.
     */
    data class UiState(
        val name: String = "",
        val description: String = "",
        val category: String = "Select Category",
        val startDate: String = "",
        val endDate: String = "",
        val frequency: String = "Each Day",
        val showCategorySheet: Boolean = false,
        val showDateRangePicker: Boolean = false,
        val isValid: Boolean = false // Validation state
    )

    /**
     * Sealed class to represent all possible form events.
     */
    sealed class FormEvent {
        data class NameChanged(val name: String) : FormEvent()
        data class DescriptionChanged(val description: String) : FormEvent()
        data class CategoryChanged(val category: String) : FormEvent()
        data class DateRangeChanged(val startDate: String, val endDate: String) : FormEvent()
        data class FrequencyChanged(val frequency: String) : FormEvent()
        object SubmitGoal : FormEvent()
        object ToggleCategorySheet : FormEvent()
        object ToggleDateRangePicker : FormEvent()
    }
}



