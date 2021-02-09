package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.core.util.DateHandler
import com.example.crossingschedule.feature.schedule.domain.model.CrossingDailyActivities
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActivitiesForDay @Inject constructor(
    private val dateHandler: DateHandler,
    private val repository: ActivitiesRepository
) {
    suspend operator fun invoke(
        year: Int,
        month: Int,
        day: Int
    ): Flow<Either<Failure, CrossingDailyActivities>> {
        val formattedDate = dateHandler.formatYearDayMonthToDesiredFormat(
            year,
            month,
            day
        )

        return repository.getActivitiesFoSpecifiedDay(formattedDate)
    }

}