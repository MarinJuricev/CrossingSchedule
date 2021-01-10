package com.example.crossingschedule.domain.usecase.schedule

import com.example.crossingschedule.domain.model.CrossingTodo
import com.example.crossingschedule.domain.repository.ActivitiesRepository
import javax.inject.Inject

class TodoItemDoneClicked @Inject constructor(
    private val repository: ActivitiesRepository
) {

    suspend operator fun invoke(
        currentList: List<CrossingTodo>,
        clickedItem: CrossingTodo
    ) {
        //TODO add a ID field, don't just compare with the message
        val updatedList = currentList.map { currentTodo ->
            if (currentTodo.message == clickedItem.message)
                clickedItem.copy(isDone = !clickedItem.isDone)
            else
                currentTodo
        }

        repository.updateCrossingTodoItems(updatedList)
    }
}