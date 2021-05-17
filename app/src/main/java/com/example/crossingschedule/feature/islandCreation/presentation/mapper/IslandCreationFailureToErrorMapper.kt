package com.example.crossingschedule.feature.islandCreation.presentation.mapper

import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.islandCreation.domain.model.IslandCreationFailure
import com.example.crossingschedule.feature.islandCreation.presentation.model.IslandCreationError

class IslandCreationFailureToErrorMapper: Mapper<IslandCreationError, IslandCreationFailure> {

    override suspend fun map(origin: IslandCreationFailure): IslandCreationError =
        with(origin){

        }
}