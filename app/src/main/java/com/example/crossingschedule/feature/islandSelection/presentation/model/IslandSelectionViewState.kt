package com.example.crossingschedule.feature.islandSelection.presentation.model

import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandInfo

data class IslandSelectionViewState(
    val isLoading: Boolean = false,
    val islandData: List<IslandInfo> = listOf(),
    val unfilteredIslandData: List<IslandInfo> = listOf(),
    val filterIslandExpanded: Boolean = false,
    val hemisphereToFilter: Hemisphere? = null,
    val errorMessage: String = "", // TODO: 4/5/21 For now just expose a error string
)
