package com.example.crossingschedule.feature.schedule.data.mapper

import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.schedule.domain.model.CrossingTodo
import javax.inject.Inject

class CrossingTodosToDefaultCrossingTodosMapper @Inject constructor(
) : Mapper<List<CrossingTodo>, List<CrossingTodo>> {

    override fun map(origin: List<CrossingTodo>): List<CrossingTodo> =
        origin.map { crossingTodo ->
            CrossingTodo(message = crossingTodo.message, isDone = false)
        }
}