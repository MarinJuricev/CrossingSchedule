package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.AuthFailure
import com.example.crossingschedule.feature.schedule.domain.model.VillagerInteraction
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import javax.inject.Inject

class DeleteVillagerInteraction @Inject constructor(
    private val repository: ActivitiesRepository
) {

    suspend operator fun invoke(
        currentList: List<VillagerInteraction>,
        currentDate: String,
        villagerInteractionToBeDeleted: VillagerInteraction
    ): Either<AuthFailure, Unit> {
        val listToBeSent = generateListToBeSent(
            currentList,
            villagerInteractionToBeDeleted
        )

        return repository.updateVillagerInteractions(listToBeSent, currentDate)
    }

    private fun generateListToBeSent(
        currentList: List<VillagerInteraction>,
        villagerInteractionToBeDeleted: VillagerInteraction
    ): List<VillagerInteraction> =
        if (currentList.isEmpty()) {
            listOf(villagerInteractionToBeDeleted)
        } else {
            currentList.minus(villagerInteractionToBeDeleted)
        }
}