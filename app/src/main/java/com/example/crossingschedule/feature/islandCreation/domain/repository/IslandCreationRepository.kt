package com.example.crossingschedule.feature.islandCreation.domain.repository

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.feature.islandCreation.domain.model.IslandCreationFailure
import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere

interface IslandCreationRepository {
    suspend fun createIsland(
        islandName: String,
        hemisphere: Hemisphere,
        numberOfVillagers: Int
    ): Either<IslandCreationFailure, Unit>
}