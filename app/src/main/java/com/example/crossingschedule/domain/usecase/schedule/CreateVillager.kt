package com.example.crossingschedule.domain.usecase.schedule

import com.example.crossingschedule.domain.core.Either
import com.example.crossingschedule.domain.core.Failure
import com.example.crossingschedule.domain.model.VillagerInteraction
import com.example.crossingschedule.domain.repository.ActivitiesRepository
import javax.inject.Inject

class CreateVillager @Inject constructor(
    private val repository: ActivitiesRepository
) {

    suspend operator fun invoke(
        currentList: List<VillagerInteraction>,
        newVillagerName: String
    ): Either<Failure, Unit> {
        val newVillagerInteraction = VillagerInteraction(villagerName = newVillagerName)
        val updatedList = currentList.plus(newVillagerInteraction)

        return repository.updateVillagerInteractions(updatedList)
    }
}