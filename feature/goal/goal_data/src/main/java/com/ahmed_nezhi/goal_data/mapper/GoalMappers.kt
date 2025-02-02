package com.ahmed_nezhi.goal_data.mapper

import com.ahmed_nezhi.database.entity.GoalEntity
import com.ahmed_nezhi.goal_domain.model.Goal


fun GoalEntity.toDomain(): Goal {
    return Goal(
        id = this.id,
        name = this.name,
        description = this.description,
        category = this.category,
        startDate = this.startDate,
        endDate = this.endDate,
        frequency = this.frequency,
    )
}

fun Goal.toEntity(): GoalEntity {
    return GoalEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        category = this.category,
        startDate = this.startDate,
        endDate = this.endDate,
        frequency = this.frequency,
    )
}