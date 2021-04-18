package com.example.crossingschedule.feature.islandCreation.presentation.model

import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere

data class IslandCreationViewState(
    val name: String,
    val hemisphere: Hemisphere,
    val numberOfVillagers: Int,
)
