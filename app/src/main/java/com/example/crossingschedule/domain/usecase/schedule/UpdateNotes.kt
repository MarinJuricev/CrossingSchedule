package com.example.crossingschedule.domain.usecase.schedule

import com.example.crossingschedule.domain.repository.ActivitiesRepository
import javax.inject.Inject

class UpdateNotes @Inject constructor(
    private val repository: ActivitiesRepository
) {
    suspend operator fun invoke(updatedNotes: String) =
        repository.updateNotes(updatedNotes)
}