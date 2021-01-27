package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.feature.schedule.domain.model.CrossingDailyActivities
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActivitiesForDay @Inject constructor(
    private val repository: ActivitiesRepository
) {
    //TODO: Actually use the date, for now fetch the hardcoded data in Firestore
    suspend operator fun invoke(
        year: Int,
        month: Int,
        day: Int
    ): Flow<Either<Failure, CrossingDailyActivities>> =
        repository.getActivitiesFoSpecifiedDay("12.01.2021")
}