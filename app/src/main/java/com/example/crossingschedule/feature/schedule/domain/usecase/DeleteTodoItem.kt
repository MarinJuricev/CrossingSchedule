package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.feature.schedule.domain.model.CrossingTodo
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import javax.inject.Inject

class DeleteTodoItem @Inject constructor(
    private val repository: ActivitiesRepository
) {

    suspend operator fun invoke(
        currentList: List<CrossingTodo>?,
        todoToBeDeleted: CrossingTodo
    ): Either<Failure, Unit> {
        val listToBeSent = generateListToBeSent(currentList, todoToBeDeleted)

        return repository.updateCrossingTodoItems(listToBeSent)
    }

    private fun generateListToBeSent(
        currentList: List<CrossingTodo>?,
        todoToBeDeleted: CrossingTodo
    ): List<CrossingTodo> =
        if (currentList.isNullOrEmpty()) {
            listOf(todoToBeDeleted)
        } else {
            currentList.minus(todoToBeDeleted)
        }
}