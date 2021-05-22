package com.example.crossingschedule.feature.islandCreation.presentation.mapper

import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.islandCreation.domain.model.IslandCreationFailure
import com.example.crossingschedule.feature.islandCreation.domain.model.IslandCreationFailure.*
import com.example.crossingschedule.feature.islandCreation.presentation.model.IslandCreationError
import com.example.crossingschedule.feature.islandCreation.presentation.model.IslandCreationError.*
import javax.inject.Inject

class IslandCreationFailureToErrorMapper @Inject constructor(
) : Mapper<IslandCreationError, IslandCreationFailure> {

    override suspend fun map(origin: IslandCreationFailure): IslandCreationError =
        when (origin) {
            is InvalidIslandName -> NameError(origin.errorMessage)
            is InvalidHemisphere -> HemisphereError(origin.errorMessage)
            is InvalidNumberOfVillagers -> NumberOfVillagersError(origin.errorMessage)
            is RemoteFailure -> GeneralError(origin.errorMessage)
        }
}