package com.example.crossingschedule.presentation.schedule.model

import java.util.*

data class DateOptions(
    val year: Int = 0,
    val month: Int = 0,
    val day: Int = 0,
    val formattedDate: String = "",
    val date: Date = Date()
)