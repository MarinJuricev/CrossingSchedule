package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import javax.inject.Inject

class UpdateNotes @Inject constructor(
    private val repository: ActivitiesRepository
) {
    suspend operator fun invoke(
        updatedNotes: String,
        currentDate: String
    ) = repository.updateNotes(updatedNotes, currentDate)
}