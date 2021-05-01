package com.example.crossingschedule.feature.islandSelection.domain.repository

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandInfo
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandSelectionFailure

interface IslandSelectionRepository {
    suspend fun getIslands(): Either<IslandSelectionFailure, List<IslandInfo>>
}