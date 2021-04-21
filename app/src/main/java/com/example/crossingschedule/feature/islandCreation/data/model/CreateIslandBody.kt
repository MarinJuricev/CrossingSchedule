package com.example.crossingschedule.feature.islandCreation.data.model

import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateIslandBody(
    val islandName: String,
    val hemisphere: Hemisphere,
    val numberOfVillagers: Int,
)
