package com.example.crossingschedule.feature.islandCreation.data.model

import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IslandResponse(
    val islandId: Int,
    val islandName: String,
    val hemisphere: Hemisphere,
    val numberOfVillagers: Int,
)