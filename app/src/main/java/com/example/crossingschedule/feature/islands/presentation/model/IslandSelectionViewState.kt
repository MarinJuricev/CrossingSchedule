package com.example.crossingschedule.feature.islands.presentation.model

import com.example.crossingschedule.feature.islands.domain.model.IslandInfo

data class IslandSelectionViewState(
    val islandData: List<IslandInfo> = listOf()
)
