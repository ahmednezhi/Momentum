package com.ahmed_nezhi.goal_domain.usecase

import com.ahmed_nezhi.common.enums.FrequencyEnum
import com.ahmed_nezhi.database.Constant.dateFormatter
import com.ahmed_nezhi.goal_domain.model.Goal
import com.ahmed_nezhi.goal_domain.repository.GoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class GetAllGoalsUseCase @Inject constructor(
    private val repository: GoalRepository
) {
    operator fun invoke(date: String): Flow<List<Goal>> {
        return repository.getAllGoalsOfDate(date).map { goals ->
            goals.filter { goal ->
                when (goal.frequency) {
                    FrequencyEnum.DAILY -> true // Always include daily goals
                    FrequencyEnum.EACH_TWO_DAYS -> isEveryTwoDays(goal.startDate, date)
                    FrequencyEnum.ONCE_A_WEEK -> isSameDayOfWeek(goal.startDate, date)
                    FrequencyEnum.ONCE_A_MONTH -> isSameDayOfMonth(goal.startDate, date)
                }
            }
        }
    }

    private fun isEveryTwoDays(startDate: String, currentDate: String): Boolean {
        val formatter = dateFormatter
        val start = LocalDate.parse(startDate, formatter)
        val current = LocalDate.parse(currentDate, formatter)
        val daysBetween = ChronoUnit.DAYS.between(start, current)
        return daysBetween % 2 == 0L
    }

    private fun isSameDayOfWeek(startDate: String, currentDate: String): Boolean {
        val formatter = dateFormatter
        val start = LocalDate.parse(startDate, formatter)
        val current = LocalDate.parse(currentDate, formatter)
        return start.dayOfWeek == current.dayOfWeek
    }

    private fun isSameDayOfMonth(startDate: String, currentDate: String): Boolean {
        val formatter = dateFormatter
        val start = LocalDate.parse(startDate, formatter)
        val current = LocalDate.parse(currentDate, formatter)
        return start.dayOfMonth == current.dayOfMonth
    }
}