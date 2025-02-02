package com.ahmed_nezhi.goal_domain.usecase

import com.ahmed_nezhi.goal_domain.model.GoalProgress
import com.ahmed_nezhi.goal_domain.repository.GoalProgressRepository
import javax.inject.Inject

class InsertGoalProgressUseCase @Inject constructor(
    private val goalProgressRepository: GoalProgressRepository
) {
    suspend operator fun invoke(progress: GoalProgress) {
        goalProgressRepository.insertGoalProgress(progress)
    }
}
