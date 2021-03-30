package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure
import com.example.crossingschedule.feature.schedule.domain.model.CrossingTodo
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import javax.inject.Inject

class TodoItemDoneClicked @Inject constructor(
    private val repository: ActivitiesRepository
) {

    suspend operator fun invoke(
        currentList: List<CrossingTodo>,
        currentDate: String,
        clickedItem: CrossingTodo
    ): Either<AuthFailure, Unit> {
        //TODO add a ID field, don't just compare with the message
        val listToBeSent = generateListToBeSent(currentList, clickedItem)

        return repository.updateCrossingTodoItems(listToBeSent, currentDate)
    }

    private fun generateListToBeSent(
        currentList: List<CrossingTodo>,
        clickedItem: CrossingTodo
    ): List<CrossingTodo> =
        if (currentList.isEmpty()) {
            listOf(clickedItem)
        } else {
            currentList.map { currentTodo ->
                if (currentTodo.message == clickedItem.message)
                    clickedItem.copy(isDone = !clickedItem.isDone)
                else
                    currentTodo
            }
        }
}