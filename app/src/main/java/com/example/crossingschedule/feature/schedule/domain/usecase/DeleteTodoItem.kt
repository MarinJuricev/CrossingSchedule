package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure
import com.example.crossingschedule.feature.schedule.domain.model.CrossingTodo
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import javax.inject.Inject

class DeleteTodoItem @Inject constructor(
    private val repository: ActivitiesRepository
) {

    suspend operator fun invoke(
        currentList: List<CrossingTodo>,
        currentDate: String,
        todoToBeDeleted: CrossingTodo
    ): Either<AuthFailure, Unit> {
        val listToBeSent = generateListToBeSent(currentList, todoToBeDeleted)

        return repository.updateCrossingTodoItems(listToBeSent, currentDate)
    }

    private fun generateListToBeSent(
        currentList: List<CrossingTodo>,
        todoToBeDeleted: CrossingTodo
    ): List<CrossingTodo> =
        if (currentList.isEmpty()) {
            listOf(todoToBeDeleted)
        } else {
            currentList.minus(todoToBeDeleted)
        }
}