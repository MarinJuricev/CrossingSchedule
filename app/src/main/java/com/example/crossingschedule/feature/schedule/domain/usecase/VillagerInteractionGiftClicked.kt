package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.Failure
import com.example.crossingschedule.feature.schedule.domain.model.VillagerInteraction
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import javax.inject.Inject

class VillagerInteractionGiftClicked @Inject constructor(
    private val repository: ActivitiesRepository
) {

    suspend operator fun invoke(
        currentDate: String,
        villagerInteractionToChange: VillagerInteraction,
        currentVillagerInteraction: List<VillagerInteraction>
    ): Either<Failure, Unit> {
        val updatedList = currentVillagerInteraction.map {
            if (villagerInteractionToChange.villagerName == it.villagerName) {
                villagerInteractionToChange.copy(receivedGift = !villagerInteractionToChange.receivedGift)
            } else {
                it
            }
        }

        return repository.updateVillagerInteractions(updatedList, currentDate)
    }
}
