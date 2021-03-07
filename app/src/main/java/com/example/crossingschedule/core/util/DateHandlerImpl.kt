package com.example.crossingschedule.core.util

import com.example.crossingschedule.core.model.CrossingDay
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

interface DateHandler {
    fun formatYearDayMonthToDesiredFormat(
        year: Int,
        month: Int,
        day: Int
    ): String

    fun provideCurrentCrossingDay(): CrossingDay
}

class DateHandlerImpl @Inject constructor() : DateHandler {
    override fun formatYearDayMonthToDesiredFormat(
        year: Int,
        month: Int,
        day: Int
    ): String {
        val formattedDate = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, day)
        }
        val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        return format.format(formattedDate.time)
    }

    override fun provideCurrentCrossingDay(): CrossingDay {
        val calendar = Calendar.getInstance()

        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        return CrossingDay(currentYear, currentMonth, currentDay)
    }
}