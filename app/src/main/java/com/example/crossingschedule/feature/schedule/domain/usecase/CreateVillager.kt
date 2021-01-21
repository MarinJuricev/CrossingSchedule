package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.feature.schedule.domain.model.VillagerInteraction
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import javax.inject.Inject

class CreateVillager @Inject constructor(
    private val repository: ActivitiesRepository
) {

    suspend operator fun invoke(
        currentList: List<VillagerInteraction>?,
        newVillagerName: String
    ): Either<Failure, Unit> {
        val newVillagerInteraction = VillagerInteraction(villagerName = newVillagerName)
        val listToBeSent = generateListToBeSent(currentList, newVillagerInteraction)

        return repository.updateVillagerInteractions(listToBeSent)
    }

    private fun generateListToBeSent(
        currentList: List<VillagerInteraction>?,
        newVillagerInteraction: VillagerInteraction
    ): List<VillagerInteraction> =
        if (currentList.isNullOrEmpty()) {
            listOf(newVillagerInteraction)
        } else {
            currentList.plus(newVillagerInteraction)
        }
}