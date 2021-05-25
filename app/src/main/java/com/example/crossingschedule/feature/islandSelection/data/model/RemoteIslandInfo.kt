package com.example.crossingschedule.feature.islandSelection.data.model

import com.example.crossingschedule.core.model.CrossingDay
import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteIslandInfo(
    val id: String,
    val name: String,
    val hemisphere: Hemisphere,
    val numberOfVillagers: Int,
    val lastVisited: Long,
)
