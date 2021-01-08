package com.example.crossingschedule.di

import com.example.crossingschedule.domain.core.Mapper
import com.example.crossingschedule.domain.model.CrossingDailyActivities
import com.example.crossingschedule.domain.model.Shop
import com.example.crossingschedule.presentation.schedule.mapper.ActivitiesToScheduleViewStateMapper
import com.example.crossingschedule.presentation.schedule.mapper.UiShopsToShopsMapper
import com.example.crossingschedule.presentation.schedule.model.ScheduleViewState
import com.example.crossingschedule.presentation.schedule.model.UiShop
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ScheduleModule {

    @Provides
    fun providesViewStateMapper(
        activitiesToScheduleViewStateMapper: ActivitiesToScheduleViewStateMapper
    ): Mapper<ScheduleViewState, CrossingDailyActivities> =
        activitiesToScheduleViewStateMapper

    @Provides
    fun providesUiShopsToShopsMapper(
        uiShopsToShopsMapper: UiShopsToShopsMapper
    ): Mapper<List<Shop>, List<UiShop>> = uiShopsToShopsMapper

}