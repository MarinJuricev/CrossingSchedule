package com.example.crossingschedule.domain.repository

import com.example.crossingschedule.domain.core.Either
import com.example.crossingschedule.domain.core.Failure
import com.example.crossingschedule.domain.model.CrossingDailyActivities
import com.example.crossingschedule.domain.model.CrossingTodo
import com.example.crossingschedule.domain.model.Shop
import kotlinx.coroutines.flow.Flow

interface ActivitiesRepository {
    suspend fun getCurrentActivities(): Flow<Either<Failure, CrossingDailyActivities>>
    suspend fun updateCrossingTodoItems(updatedList: List<CrossingTodo>): Either<Failure, Unit>
    suspend fun updateShopItems(updatedList: List<Shop>): Either<Failure, Unit>
    suspend fun updateNotes(updatedNotes: String): Either<Failure, Unit>
}