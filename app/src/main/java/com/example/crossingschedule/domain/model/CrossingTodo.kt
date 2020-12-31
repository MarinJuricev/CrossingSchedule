package com.example.crossingschedule.domain.model

data class CrossingTodo(
    val message: String = "",
    @field:JvmField
    val isDone: Boolean = false,
)