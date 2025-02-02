package com.ahmed_nezhi.goal_domain.usecase

import com.ahmed_nezhi.goal_domain.model.GoalProgress
import com.ahmed_nezhi.goal_domain.repository.GoalProgressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGoalProgressUseCase @Inject constructor(
    private val goalProgressRepository: GoalProgressRepository
) {
    operator fun invoke(goalId: Int): Flow<List<GoalProgress>> {
        return goalProgressRepository.getGoalProgress(goalId)
    }
}
