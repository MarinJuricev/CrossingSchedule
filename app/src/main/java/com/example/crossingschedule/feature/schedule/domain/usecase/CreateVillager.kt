package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure
import com.example.crossingschedule.feature.schedule.domain.model.VillagerInteraction
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import javax.inject.Inject

class CreateVillager @Inject constructor(
    private val repository: ActivitiesRepository
) {

    suspend operator fun invoke(
        currentList: List<VillagerInteraction>,
        currentDate: String,
        newVillagerName: String
    ): Either<AuthFailure, Unit> {
        val newVillagerInteraction = VillagerInteraction(villagerName = newVillagerName)
        val listToBeSent = generateListToBeSent(currentList, newVillagerInteraction)

        return repository.updateVillagerInteractions(listToBeSent, currentDate)
    }

    private fun generateListToBeSent(
        currentList: List<VillagerInteraction>,
        newVillagerInteraction: VillagerInteraction
    ): List<VillagerInteraction> =
        if (currentList.isEmpty()) {
            listOf(newVillagerInteraction)
        } else {
            currentList.plus(newVillagerInteraction)
        }
}