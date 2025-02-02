package com.ahmed_nezhi.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmed_nezhi.database.entity.GoalProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoalProgress(progress: GoalProgressEntity)

    @Query("SELECT * FROM goal_progress WHERE goalId = :goalId ORDER BY date DESC")
    fun getGoalProgress(goalId: Int): Flow<List<GoalProgressEntity>>

    @Delete
    suspend fun deleteGoalProgress(progress: GoalProgressEntity)
}
