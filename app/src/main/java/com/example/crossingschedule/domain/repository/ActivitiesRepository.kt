package com.example.crossingschedule.domain.repository

import com.example.crossingschedule.domain.core.Either
import com.example.crossingschedule.domain.core.Failure
import com.example.crossingschedule.domain.model.CrossingDailyActivities
import kotlinx.coroutines.flow.Flow

interface ActivitiesRepository {
    suspend fun getCurrentActivities(): Flow<Either<Failure, CrossingDailyActivities>>
}