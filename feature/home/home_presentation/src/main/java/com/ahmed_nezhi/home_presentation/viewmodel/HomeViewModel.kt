package com.ahmed_nezhi.home_presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed_nezhi.goal_domain.model.Goal
import com.ahmed_nezhi.goal_domain.usecase.GetAllGoalsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllGoalsUseCase: GetAllGoalsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> get() = _uiState

    private val _currentSelectedDate =
        MutableStateFlow(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
    val currentSelectedDate: StateFlow<String> get() = _currentSelectedDate

    init {
        observeSelectedDate()
    }

    private fun observeSelectedDate() {
        viewModelScope.launch {
            _currentSelectedDate.collectLatest { selectedDate ->
                fetchGoals(selectedDate)
            }
        }
    }

    fun updateSelectedDate(selectedDate: String) {
        _currentSelectedDate.update { selectedDate }
    }

    private fun fetchGoals(selectedDate: String) {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            getAllGoalsUseCase(selectedDate)
                .catch { error ->
                    _uiState.update {
                        it.copy(
                            goals = emptyList(),
                            isLoading = false,
                            errorMessage = error.localizedMessage
                        )
                    }
                }
                .collectLatest { goals ->
                    _uiState.update { it.copy(goals = goals, isLoading = false) }
                }
        }
    }

    /**
     * Home UI state data class
     */
    data class HomeUiState(
        val goals: List<Goal> = emptyList(),
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )
}
