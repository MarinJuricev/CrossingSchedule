package com.example.crossingschedule.feature.schedule.di

import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.schedule.data.mapper.CrossingTodosToDefaultCrossingTodosMapper
import com.example.crossingschedule.feature.schedule.data.repository.ActivitiesRepositoryImpl
import com.example.crossingschedule.feature.schedule.domain.model.CrossingDailyActivities
import com.example.crossingschedule.feature.schedule.domain.model.CrossingTodo
import com.example.crossingschedule.feature.schedule.domain.model.Shop
import com.example.crossingschedule.feature.schedule.domain.model.TurnipPrices
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import com.example.crossingschedule.feature.schedule.presentation.mapper.ActivitiesToScheduleViewStateMapper
import com.example.crossingschedule.feature.schedule.presentation.mapper.UiShopsToShopsMapper
import com.example.crossingschedule.feature.schedule.presentation.mapper.UiTurnipPricesToTurnipPricesMapper
import com.example.crossingschedule.feature.schedule.presentation.model.ScheduleViewState
import com.example.crossingschedule.feature.schedule.presentation.model.UiShop
import com.example.crossingschedule.feature.schedule.presentation.model.UiTurnipPrices
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

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

    @Provides
    fun providesUiTurnipPricesToTurnipPricesMapper(
        uiTurnipPricesToTurnipPricesMapper: UiTurnipPricesToTurnipPricesMapper
    ): Mapper<TurnipPrices, UiTurnipPrices> = uiTurnipPricesToTurnipPricesMapper

    @Provides
    fun provideActivitiesRepository(
        firebaseFireStore: FirebaseFirestore,
        crossingTodosToDefaultCrossingTodosMapper: Mapper<List<CrossingTodo>, List<CrossingTodo>>
    ): ActivitiesRepository =
        ActivitiesRepositoryImpl(
            firebaseFireStore,
            crossingTodosToDefaultCrossingTodosMapper
        )

    @Provides
    fun providesCrossingTodosToDefaultCrossingTodosMapper(
        crossingTodosToDefaultCrossingTodosMapper: CrossingTodosToDefaultCrossingTodosMapper
    ): Mapper<List<CrossingTodo>, List<CrossingTodo>> = crossingTodosToDefaultCrossingTodosMapper
}