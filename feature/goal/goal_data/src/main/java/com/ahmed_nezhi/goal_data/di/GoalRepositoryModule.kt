package com.ahmed_nezhi.goal_data.di

import com.ahmed_nezhi.database.dao.GoalDao
import com.ahmed_nezhi.database.dao.GoalProgressDao
import com.ahmed_nezhi.goal_data.repository.GoalProgressRepositoryImpl
import com.ahmed_nezhi.goal_data.repository.GoalRepositoryImpl
import com.ahmed_nezhi.goal_domain.repository.GoalProgressRepository
import com.ahmed_nezhi.goal_domain.repository.GoalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GoalRepositoryModule {

    @Provides
    @Singleton
    fun provideGoalRepository(goalDao: GoalDao): GoalRepository {
        return GoalRepositoryImpl(goalDao)
    }

    @Provides
    @Singleton
    fun provideGoalProgressRepository(
        goalProgressDao: GoalProgressDao
    ): GoalProgressRepository {
        return GoalProgressRepositoryImpl(goalProgressDao)
    }
}
