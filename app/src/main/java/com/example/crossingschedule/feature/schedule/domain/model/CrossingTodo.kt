package com.example.crossingschedule.feature.schedule.domain.model

data class CrossingTodo(
    val message: String = "",
    @field:JvmField
    val isDone: Boolean = false,
)