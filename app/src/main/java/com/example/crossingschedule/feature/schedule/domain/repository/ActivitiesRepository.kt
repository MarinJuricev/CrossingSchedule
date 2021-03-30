package com.example.crossingschedule.feature.schedule.domain.repository

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.AuthFailure
import com.example.crossingschedule.feature.schedule.domain.model.*
import kotlinx.coroutines.flow.Flow

interface ActivitiesRepository {
    suspend fun getActivitiesFoSpecifiedDay(selectedDate: String): Flow<Either<AuthFailure, CrossingDailyActivities>>
    suspend fun getDefaultIslandActivities(): Either<AuthFailure, CrossingDailyActivities>
    suspend fun updateCrossingTodoItems(
        updatedList: List<CrossingTodo>,
        currentDate: String
    ): Either<AuthFailure, Unit>

    suspend fun updateShopItems(
        updatedList: List<Shop>,
        currentDate: String
    ): Either<AuthFailure, Unit>

    suspend fun updateNotes(
        updatedNotes: String,
        currentDate: String
    ): Either<AuthFailure, Unit>

    suspend fun updateVillagerInteractions(
        updatedList: List<VillagerInteraction>,
        currentDate: String
    ): Either<AuthFailure, Unit>

    suspend fun updateTurnipPrices(
        updatedTurnipPrices: TurnipPrices,
        currentDate: String
    ): Either<AuthFailure, Unit>

}