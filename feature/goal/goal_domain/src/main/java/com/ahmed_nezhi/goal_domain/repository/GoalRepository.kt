package com.ahmed_nezhi.goal_domain.repository

import com.ahmed_nezhi.goal_domain.model.Goal
import kotlinx.coroutines.flow.Flow

interface GoalRepository {
    suspend fun insertGoal(goal: Goal)
    fun getAllGoalsOfDate(date : String): Flow<List<Goal>>
    fun getGoalById(id : Int): Flow<Goal>
}
