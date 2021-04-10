package com.example.crossingschedule.feature.islands.presentation.model

import com.example.crossingschedule.feature.islands.domain.model.IslandInfo

data class IslandSelectionViewState(
    val islandData: List<IslandInfo> = listOf(),
    val filterIslandExpanded: Boolean = false,
    val errorMessage: String = "", // TODO: 4/5/21 For now just expose a error string
)
