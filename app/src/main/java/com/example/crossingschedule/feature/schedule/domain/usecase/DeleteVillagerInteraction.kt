package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.feature.schedule.domain.model.VillagerInteraction
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import javax.inject.Inject

class DeleteVillagerInteraction @Inject constructor(
    private val repository: ActivitiesRepository
) {

    suspend operator fun invoke(
        currentList: List<VillagerInteraction>?,
        villagerInteractionToBeDeleted: VillagerInteraction
    ): Either<Failure, Unit> {
        val listToBeSent = generateListToBeSent(
            currentList,
            villagerInteractionToBeDeleted
        )

        return repository.updateVillagerInteractions(listToBeSent)
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