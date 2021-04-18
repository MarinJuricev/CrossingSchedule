package com.example.crossingschedule.feature.islandCreation.domain.model

import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere

data class IslandCreationInfo(
    val name: String,
    val hemisphere: Hemisphere,
    val numberOfVillagers: Int,
)
