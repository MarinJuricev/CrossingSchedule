package com.example.crossingschedule.feature.islandSelection.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IslandResponse(
    val availableIslands: List<RemoteIslandInfo>
)
