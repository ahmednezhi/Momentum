package com.ahmed_nezhi.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ahmed_nezhi.common.enums.CategoryEnum
import com.ahmed_nezhi.common.enums.FrequencyEnum

@Entity(tableName = "goals")
data class GoalEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val category: CategoryEnum,
    val startDate: String,
    val endDate: String,
    val frequency: FrequencyEnum,
)