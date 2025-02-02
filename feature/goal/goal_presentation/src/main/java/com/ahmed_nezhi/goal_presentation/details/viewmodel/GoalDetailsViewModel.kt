package com.ahmed_nezhi.goal_presentation.details.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed_nezhi.common.enums.GoalStatus
import com.ahmed_nezhi.goal_domain.model.Goal
import com.ahmed_nezhi.goal_domain.model.GoalProgress
import com.ahmed_nezhi.goal_domain.usecase.GetGoalByIdUseCase
import com.ahmed_nezhi.goal_domain.usecase.GetGoalProgressUseCase
import com.ahmed_nezhi.goal_domain.usecase.InsertGoalProgressUseCase
import com.ahmed_nezhi.goal_presentation.details.toTimeLineDataList
import com.ahmed_nezhi.ui.timeline.models.TimeLineData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@HiltViewModel
class GoalDetailsViewModel @Inject constructor(
    private val getGoalByIdUseCase: GetGoalByIdUseCase,
    private val getGoalProgressUseCase: GetGoalProgressUseCase,
    private val addGoalProgressUseCase: InsertGoalProgressUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(GoalDetailsUiState(isLoading = true))
    val uiState: StateFlow<GoalDetailsUiState> = _uiState.asStateFlow()

    fun loadGoal(goalId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                getGoalByIdUseCase(goalId).collect { goal ->
                    _uiState.update {
                        it.copy(goal = goal, isLoading = false)
                    }
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.localizedMessage ?: "Unknown error")
                }
            }
        }
    }
    fun loadGoalProgress(goalId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                getGoalProgressUseCase(goalId).collect { progressList ->
                    _uiState.update {
                        val timeLineData = progressList.toTimeLineDataList(
                            uiState.value.goal?.name ?: "",
                            uiState.value.goal?.description ?: ""
                        )
                        it.copy(
                            progress = timeLineData,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.localizedMessage ?: "Unknown error")
                }
            }
        }
    }

    fun addProgress(goalId: Int, thoughts: String, imageUri: Uri) {
        viewModelScope.launch {
            try {
                val goalProgress = GoalProgress(
                    goalId = goalId,
                    description = thoughts,
                    photoUrl = imageUri.toString(),
                    date = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE),
                    status = GoalStatus.IN_PROGRESS
                )
                addGoalProgressUseCase(goalProgress)
                getGoalProgressUseCase(goalId).collect { progressList ->
                    _uiState.update {
                        it.copy(
                            progress = progressList.toTimeLineDataList(
                                _uiState.value.goal?.name ?: "",
                                _uiState.value.goal?.description ?: ""
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "Failed to save progress")
                }
            }
        }
    }

    /**
     * UI state for Goal Details screen
     */
    data class GoalDetailsUiState(
        val goal: Goal? = null,
        val progress: List<TimeLineData> = emptyList(),
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )
}