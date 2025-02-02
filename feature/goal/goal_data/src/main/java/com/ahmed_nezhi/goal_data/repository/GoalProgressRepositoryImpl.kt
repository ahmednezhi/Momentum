package com.ahmed_nezhi.goal_data.repository

import com.ahmed_nezhi.database.dao.GoalProgressDao
import com.ahmed_nezhi.goal_data.mapper.toDomainModel
import com.ahmed_nezhi.goal_data.mapper.toEntity
import com.ahmed_nezhi.goal_domain.model.GoalProgress
import com.ahmed_nezhi.goal_domain.repository.GoalProgressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GoalProgressRepositoryImpl @Inject constructor(
    private val goalProgressDao: GoalProgressDao
) : GoalProgressRepository {

    override suspend fun insertGoalProgress(progress: GoalProgress) {
        goalProgressDao.insertGoalProgress(progress.toEntity())
    }

    override fun getGoalProgress(goalId: Int): Flow<List<GoalProgress>> {
        return goalProgressDao.getGoalProgress(goalId).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
}
