package com.example.crossingschedule.di

import com.example.crossingschedule.domain.core.Mapper
import com.example.crossingschedule.domain.model.CrossingDailyActivities
import com.example.crossingschedule.presentation.schedule.model.ScheduleViewState
import com.example.crossingschedule.presentation.schedule.mapper.ActivitiesToScheduleViewStateMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class ScheduleModule {

    @Binds
    abstract fun bindViewStateMapper(
        activitiesToScheduleViewStateMapper: ActivitiesToScheduleViewStateMapper
    ): Mapper<ScheduleViewState, CrossingDailyActivities>

}