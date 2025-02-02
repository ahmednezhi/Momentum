package com.ahmed_nezhi.goal_domain.di

import com.ahmed_nezhi.goal_domain.repository.GoalRepository
import com.ahmed_nezhi.goal_domain.usecase.GetAllGoalsUseCase
import com.ahmed_nezhi.goal_domain.usecase.GetGoalByIdUseCase
import com.ahmed_nezhi.goal_domain.usecase.InsertGoalUseCase
import com.ahmed_nezhi.goal_domain.usecase.ScheduleNotificationsUseCase
import com.ahmed_nezhi.notifications.AlarmScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideInsertGoalUseCase(repository: GoalRepository): InsertGoalUseCase {
        return InsertGoalUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllGoalsUseCase(repository: GoalRepository): GetAllGoalsUseCase {
        return GetAllGoalsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetGoalByIdUseCase(repository: GoalRepository): GetGoalByIdUseCase {
        return GetGoalByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideScheduleNotificationsUseCase(alarmScheduler: AlarmScheduler): ScheduleNotificationsUseCase {
        return ScheduleNotificationsUseCase(alarmScheduler)
    }
}