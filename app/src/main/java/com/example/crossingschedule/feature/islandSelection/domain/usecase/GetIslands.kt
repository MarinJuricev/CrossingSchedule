package com.example.crossingschedule.feature.islandSelection.domain.usecase

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandInfo
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandSelectionFailure
import com.example.crossingschedule.feature.islandSelection.domain.repository.IslandSelectionRepository
import javax.inject.Inject

class GetIslands @Inject constructor(
    private val islandSelectionRepository: IslandSelectionRepository
) {

    suspend operator fun invoke(): Either<IslandSelectionFailure, List<IslandInfo>> =
        islandSelectionRepository.getIslands()
}