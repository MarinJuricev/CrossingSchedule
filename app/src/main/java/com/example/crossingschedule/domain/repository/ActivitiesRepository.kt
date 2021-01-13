package com.example.crossingschedule.domain.repository

import com.example.crossingschedule.domain.core.Either
import com.example.crossingschedule.domain.core.Failure
import com.example.crossingschedule.domain.model.*
import kotlinx.coroutines.flow.Flow

interface ActivitiesRepository {
    suspend fun getActivitiesForSpecifiedDay(): Flow<Either<Failure, CrossingDailyActivities>>
    suspend fun getDefaultIslandActivities(): Either<Failure, CrossingDailyActivities>
    suspend fun updateCrossingTodoItems(updatedList: List<CrossingTodo>): Either<Failure, Unit>
    suspend fun updateShopItems(updatedList: List<Shop>): Either<Failure, Unit>
    suspend fun updateNotes(updatedNotes: String): Either<Failure, Unit>
    suspend fun updateVillagerInteractions(updatedList: List<VillagerInteraction>): Either<Failure, Unit>
    suspend fun updateTurnipPrices(updatedTurnipPrices: TurnipPrices): Either<Failure, Unit>

}