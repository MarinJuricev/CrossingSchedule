package com.example.crossingschedule.presentation.schedule.mapper

import com.example.crossingschedule.domain.core.Mapper
import com.example.crossingschedule.domain.model.CrossingDailyActivities
import com.example.crossingschedule.presentation.schedule.ScheduleViewState

class ActivitiesToScheduleViewStateMapper : Mapper<ScheduleViewState, CrossingDailyActivities> {

    override fun map(origin: CrossingDailyActivities): ScheduleViewState {
        return with(origin) {
            ScheduleViewState(
                isLoading = false,
                errorMessage = "",
                currentDate = currentDate.toString(),
                shops = shops,
                crossingTodos = crossingTodos,
                notes = notes,
                turnipPrices = turnipPrices,
                villagersInteraction = villagersInteraction
            )
        }
    }
}