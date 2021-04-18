package com.example.crossingschedule

import com.example.crossingschedule.core.model.CrossingDay
import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandInfo

val fakeCrossingDay = CrossingDay(
    year = -1,
    month = -1,
    day = -1
)

val fakeIslandInfo = IslandInfo(
    id = "",
    name = "",
    hemisphere = Hemisphere.NORTH,
    numberOfVillagers = -1,
    lastVisited = fakeCrossingDay
)
