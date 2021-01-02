package com.example.crossingschedule.domain.model

import java.util.*

data class CrossingDailyActivities(
    val currentDate: Date = Date(),
    val shops: List<Shop> = emptyList(),
    val crossingTodos: List<CrossingTodo> = emptyList(),
    val notes: String = "",
    val turnipPrices: TurnipPrices = TurnipPrices(TURNIP_DEFAULT_VALUE, TURNIP_DEFAULT_VALUE),
    val villagerInteractions: List<VillagerInteraction> = emptyList()
)
