package com.example.crossingschedule.feature.islands.domain.model

import com.example.crossingschedule.core.model.CrossingDay

data class IslandInfo(
    val name: String,
    val hemisphere: Hemisphere,
    val numberOfVillagers: Int,
    val lastVisited: CrossingDay
)
