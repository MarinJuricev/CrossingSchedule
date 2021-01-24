package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.feature.schedule.domain.model.VillagerInteraction
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import javax.inject.Inject

class VillagerInteractionGiftClicked @Inject constructor(
    private val repository: ActivitiesRepository
) {

    suspend operator fun invoke(
        villagerInteractionToChange: VillagerInteraction,
        currentVillagerInteraction: List<VillagerInteraction>
    ) {
        val updatedList = currentVillagerInteraction.map {
            if (villagerInteractionToChange.villagerName == it.villagerName) {
                villagerInteractionToChange.copy(receivedGift = !villagerInteractionToChange.receivedGift)
            } else {
                it
            }
        }

        repository.updateVillagerInteractions(updatedList)
    }
}
