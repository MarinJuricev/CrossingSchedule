package com.example.crossingschedule.feature.islandCreation.domain.usecase

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.buildLeft
import com.example.crossingschedule.core.model.buildRight
import com.example.crossingschedule.feature.islandCreation.domain.model.IslandCreationFailure
import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere
import javax.inject.Inject

class IslandCreationValidator @Inject constructor() {

    fun validate(
        islandName: String,
        islandHemisphere: Hemisphere?,
        numberOfVillagers: String,
    ): Either<IslandCreationFailure, Unit> =
        when {
            islandName.isBlank() ->
                IslandCreationFailure.InvalidIslandName("Island can't be empty").buildLeft()
            islandHemisphere == null ->
                IslandCreationFailure.InvalidHemisphere("Hemisphere can't be empty").buildLeft()
            numberOfVillagers.isBlank() || isNumberOfVillagerNotANumber(numberOfVillagers) ->
                IslandCreationFailure.InvalidNumberOfVillagers("Number of villagers must be a number and must be specified")
                    .buildLeft()
            else -> Unit.buildRight()
        }

    private fun isNumberOfVillagerNotANumber(numberOfVillagers: String) =
        try {
            numberOfVillagers.toInt()
            false
        } catch (e: NumberFormatException) {
            true
        }
}