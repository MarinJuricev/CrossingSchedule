package com.example.crossingschedule.feature.islandCreation.data.repository

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.feature.islandCreation.domain.model.IslandCreationFailure
import com.example.crossingschedule.feature.islandCreation.domain.repository.IslandCreationRepository
import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere
import javax.inject.Inject

class IslandCreationRepositoryImpl @Inject constructor(

) : IslandCreationRepository {

    override suspend fun createIsland(
        islandName: String,
        hemisphere: Hemisphere,
        numberOfVillagers: Int
    ): Either<IslandCreationFailure, Unit> {
        TODO("Not yet implemented")
    }
}