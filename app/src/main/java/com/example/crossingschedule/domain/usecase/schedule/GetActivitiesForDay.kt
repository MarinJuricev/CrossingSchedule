package com.example.crossingschedule.domain.usecase.schedule

import com.example.crossingschedule.domain.core.Either
import com.example.crossingschedule.domain.core.Failure
import com.example.crossingschedule.domain.model.CrossingDailyActivities
import com.example.crossingschedule.domain.repository.ActivitiesRepository
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
        repository.getActivitiesForSpecifiedDay()
}