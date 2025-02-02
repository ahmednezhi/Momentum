package com.ahmed_nezhi.goal_domain.usecase

import com.ahmed_nezhi.goal_domain.repository.GoalRepository
import com.ahmed_nezhi.goal_domain.model.Goal
import javax.inject.Inject

class InsertGoalUseCase @Inject constructor(
    private val repository: GoalRepository
) {
    suspend operator fun invoke(goal: Goal) {
        repository.insertGoal(goal)
    }
}