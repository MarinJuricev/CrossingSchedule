package com.example.crossingschedule.domain.usecase.schedule

import com.example.crossingschedule.domain.model.CrossingTodo
import com.example.crossingschedule.domain.repository.ActivitiesRepository
import javax.inject.Inject

class DeleteTodoItem @Inject constructor(
    private val repository: ActivitiesRepository
) {

    suspend operator fun invoke(
        currentList: List<CrossingTodo>,
        todoToBeDeleted: CrossingTodo
    ) {
        val updatedList = currentList.minus(todoToBeDeleted)

        repository.updateCrossingTodoItems(updatedList)
    }
}