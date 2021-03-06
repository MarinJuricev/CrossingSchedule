package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure
import com.example.crossingschedule.feature.schedule.domain.model.VillagerInteraction
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import javax.inject.Inject

class VillagerInteractionTalkedToClicked @Inject constructor(
    private val repository: ActivitiesRepository
) {

    suspend operator fun invoke(
        currentDate: String,
        villagerInteractionToChange: VillagerInteraction,
        currentVillagerInteraction: List<VillagerInteraction>
    ): Either<AuthFailure, Unit> {
        val updatedList = currentVillagerInteraction.map {
            if (villagerInteractionToChange.villagerName == it.villagerName) {
                villagerInteractionToChange.copy(talkedTo = !villagerInteractionToChange.talkedTo)
            } else {
                it
            }
        }

        return repository.updateVillagerInteractions(updatedList, currentDate)
    }
}