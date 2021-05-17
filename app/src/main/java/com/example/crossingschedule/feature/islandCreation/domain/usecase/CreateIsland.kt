package com.example.crossingschedule.feature.islandCreation.domain.usecase

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.Either.Left
import com.example.crossingschedule.feature.islandCreation.domain.model.IslandCreationFailure
import com.example.crossingschedule.feature.islandCreation.domain.repository.IslandCreationRepository
import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere
import javax.inject.Inject

class CreateIsland @Inject constructor(
    private val islandCreationValidator: IslandCreationValidator,
    private val islandCreationRepository: IslandCreationRepository,
) {

    suspend operator fun invoke(
        islandName: String,
        hemisphere: Hemisphere?,
        numberOfVillagers: String,
    ): Either<IslandCreationFailure, Unit> {
        val validationResult = islandCreationValidator.validate(
            islandName,
            hemisphere,
            numberOfVillagers,
        )
        if (validationResult is Left) {
            return validationResult
        }

        return islandCreationRepository.createIsland(
            islandName,
            hemisphere!!, // The validator does the null check
            numberOfVillagers.toInt()//It's "safe" to call toInt since the validator does a is numericCheck
        )
    }
}