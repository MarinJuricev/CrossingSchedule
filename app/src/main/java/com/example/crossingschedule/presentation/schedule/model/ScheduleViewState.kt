package com.example.crossingschedule.presentation.schedule.model

import com.example.crossingschedule.domain.model.CrossingTodo
import com.example.crossingschedule.domain.model.TurnipPrices
import com.example.crossingschedule.domain.model.VillagerInteraction

data class ScheduleViewState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val currentDate: String = "",
    val shops: List<UiShop> = emptyList(),
    val crossingTodos: List<CrossingTodo> = emptyList(),
    val notes: String = "",
    val turnipPrices: TurnipPrices = TurnipPrices(null, null),
    val villagersInteraction: List<VillagerInteraction> = emptyList()
)