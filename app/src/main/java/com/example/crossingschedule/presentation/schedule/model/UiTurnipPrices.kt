package com.example.crossingschedule.presentation.schedule.model

const val UI_TURNIP_DEFAULT_VALUE = ""

data class UiTurnipPrices(
    val amPrice: String = UI_TURNIP_DEFAULT_VALUE,
    val pmPrice: String = UI_TURNIP_DEFAULT_VALUE,
)