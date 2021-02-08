package com.example.crossingschedule.feature.schedule.di

import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.schedule.data.factory.DefaultShopFactory
import com.example.crossingschedule.feature.schedule.data.factory.DefaultShopFactoryImpl
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
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(ViewModelComponent::class)
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
        crossingTodosToDefaultCrossingTodosMapper: Mapper<List<CrossingTodo>, List<CrossingTodo>>,
        defaultShopFactory: DefaultShopFactory
    ): ActivitiesRepository =
        ActivitiesRepositoryImpl(
            firebaseFireStore,
            crossingTodosToDefaultCrossingTodosMapper,
            defaultShopFactory
        )

    @Provides
    fun providesCrossingTodosToDefaultCrossingTodosMapper(
        crossingTodosToDefaultCrossingTodosMapper: CrossingTodosToDefaultCrossingTodosMapper
    ): Mapper<List<CrossingTodo>, List<CrossingTodo>> = crossingTodosToDefaultCrossingTodosMapper

    @Provides
    fun provideDefaultShopFactory(
        defaultShopFactory: DefaultShopFactoryImpl
    ): DefaultShopFactory = defaultShopFactory
}