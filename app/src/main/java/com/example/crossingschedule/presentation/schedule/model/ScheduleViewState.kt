package com.example.crossingschedule.presentation.schedule.model

import com.example.crossingschedule.domain.model.CrossingTodo
import com.example.crossingschedule.domain.model.VillagerInteraction

data class ScheduleViewState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val dateOptions: DateOptions = DateOptions(),
    val shops: List<UiShop> = emptyList(),
    val crossingTodos: List<CrossingTodo> = emptyList(),
    val notes: String = "",
    val turnipPrices: UiTurnipPrices = UiTurnipPrices(
        UI_TURNIP_DEFAULT_VALUE,
        UI_TURNIP_DEFAULT_VALUE
    ),
    val villagersInteraction: List<VillagerInteraction> = emptyList()
)