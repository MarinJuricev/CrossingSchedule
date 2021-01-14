package com.example.crossingschedule.data.mapper

import com.example.crossingschedule.domain.core.Mapper
import com.example.crossingschedule.domain.model.CrossingTodo
import javax.inject.Inject

class CrossingTodosToDefaultCrossingTodosMapper @Inject constructor(
) : Mapper<List<CrossingTodo>, List<CrossingTodo>> {

    override fun map(origin: List<CrossingTodo>): List<CrossingTodo> =
        origin.map { crossingTodo ->
            CrossingTodo(message = crossingTodo.message, isDone = false)
        }
}