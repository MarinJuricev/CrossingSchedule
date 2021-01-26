package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.feature.schedule.domain.model.CrossingTodo
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import javax.inject.Inject

class TodoItemDoneClicked @Inject constructor(
    private val repository: ActivitiesRepository
) {

    suspend operator fun invoke(
        currentList: List<CrossingTodo>?,
        clickedItem: CrossingTodo
    ): Either<Failure, Unit> {
        //TODO add a ID field, don't just compare with the message
        val listToBeSent = generateListToBeSent(currentList, clickedItem)

        return repository.updateCrossingTodoItems(listToBeSent)
    }

    private fun generateListToBeSent(
        currentList: List<CrossingTodo>?,
        clickedItem: CrossingTodo
    ): List<CrossingTodo> =
        if (currentList.isNullOrEmpty()) {
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