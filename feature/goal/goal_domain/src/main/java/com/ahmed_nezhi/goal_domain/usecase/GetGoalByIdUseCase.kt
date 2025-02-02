package com.ahmed_nezhi.goal_domain.usecase

import com.ahmed_nezhi.goal_domain.model.Goal
import com.ahmed_nezhi.goal_domain.repository.GoalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGoalByIdUseCase @Inject constructor(
    private val repository: GoalRepository
) {
    suspend operator fun invoke(id: Int): Flow<Goal> {
        return repository.getGoalById(id)
    }
}