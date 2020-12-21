package com.example.crossingschedule.domain.model

import java.util.Date

data class CrossingDailyActivities(
    val currentDate: Date,
    val shopStatus: ShopStatus,
    val crossingTodos: List<CrossingTodo>,
    val notes: String,
    val turnipPrices: TurnipPrices,
    val villagersInteraction: List<VillagerInteraction>
)