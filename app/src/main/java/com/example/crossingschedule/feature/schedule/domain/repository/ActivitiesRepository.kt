package com.example.crossingschedule.feature.schedule.domain.repository

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.feature.schedule.domain.model.*
import kotlinx.coroutines.flow.Flow

interface ActivitiesRepository {
    suspend fun getActivitiesFoSpecifiedDay(selectedDate: String): Flow<Either<Failure, CrossingDailyActivities>>
    suspend fun getDefaultIslandActivities(): Either<Failure, CrossingDailyActivities>
    suspend fun updateCrossingTodoItems(
        updatedList: List<CrossingTodo>,
        currentDate: String
    ): Either<Failure, Unit>

    suspend fun updateShopItems(
        updatedList: List<Shop>,
        currentDate: String
    ): Either<Failure, Unit>

    suspend fun updateNotes(
        updatedNotes: String,
        currentDate: String
    ): Either<Failure, Unit>

    suspend fun updateVillagerInteractions(
        updatedList: List<VillagerInteraction>,
        currentDate: String
    ): Either<Failure, Unit>

    suspend fun updateTurnipPrices(
        updatedTurnipPrices: TurnipPrices,
        currentDate: String
    ): Either<Failure, Unit>

}