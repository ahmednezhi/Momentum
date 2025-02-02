package com.ahmed_nezhi.goal_data.repository

import com.ahmed_nezhi.goal_data.mapper.toDomain
import com.ahmed_nezhi.goal_data.mapper.toEntity
import com.ahmed_nezhi.database.dao.GoalDao
import com.ahmed_nezhi.goal_domain.repository.GoalRepository
import com.ahmed_nezhi.goal_domain.model.Goal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GoalRepositoryImpl @Inject constructor(
    private val goalDao: GoalDao
) : GoalRepository {

    override suspend fun insertGoal(goal: Goal) {
        goalDao.insertGoal(goal.toEntity())
    }

    override fun getAllGoalsOfDate(date: String): Flow<List<Goal>> {
        return goalDao.getAllGoalsOfDate(date).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getGoalById(id: Int): Flow<Goal> {
        return goalDao.getGoalById(id).map { entity ->
            entity.toDomain()
        }

    }
}