package com.example.crossingschedule.domain.model

const val TURNIP_DEFAULT_VALUE = 0

data class TurnipPrices(
    val amPrice: Int = TURNIP_DEFAULT_VALUE,
    val pmPrice: Int = TURNIP_DEFAULT_VALUE,
)