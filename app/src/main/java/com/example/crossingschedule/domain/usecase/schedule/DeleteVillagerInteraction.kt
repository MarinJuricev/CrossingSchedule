package com.example.crossingschedule.domain.usecase.schedule

import com.example.crossingschedule.domain.model.VillagerInteraction
import com.example.crossingschedule.domain.repository.ActivitiesRepository
import javax.inject.Inject

class DeleteVillagerInteraction @Inject constructor(
    private val repository: ActivitiesRepository
) {

    suspend operator fun invoke(
        currentList: List<VillagerInteraction>?,
        villagerInteractionToBeDeleted: VillagerInteraction
    ) {
        val listToBeSent = generateListToBeSent(
            currentList,
            villagerInteractionToBeDeleted
        )

        repository.updateVillagerInteractions(listToBeSent)
    }

    private fun generateListToBeSent(
        currentList: List<VillagerInteraction>?,
        villagerInteractionToBeDeleted: VillagerInteraction
    ): List<VillagerInteraction> =
        if (currentList.isNullOrEmpty()) {
            listOf(villagerInteractionToBeDeleted)
        } else {
            currentList.minus(villagerInteractionToBeDeleted)
        }
}