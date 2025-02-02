package com.ahmed_nezhi.goal_domain.repository

import com.ahmed_nezhi.goal_domain.model.GoalProgress
import kotlinx.coroutines.flow.Flow

interface GoalProgressRepository {
    suspend fun insertGoalProgress(progress: GoalProgress)
    fun getGoalProgress(goalId: Int): Flow<List<GoalProgress>>
}