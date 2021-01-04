package com.example.crossingschedule.domain.usecase

import com.example.crossingschedule.domain.model.CrossingTodo
import com.example.crossingschedule.domain.repository.ActivitiesRepository
import javax.inject.Inject

class CreateNewTodoItem @Inject constructor(
    private val repository: ActivitiesRepository
) {

    suspend operator fun invoke(
        currentList: List<CrossingTodo>,
        newTodoItemMessage: String
    ) {
        val newTodoItem = CrossingTodo(newTodoItemMessage)
        val updatedList = currentList.plus(newTodoItem)

        repository.updateCrossingTodoItems(updatedList)
    }
}