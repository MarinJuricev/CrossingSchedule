package com.example.crossingschedule.domain.usecase.schedule

import com.example.crossingschedule.R
import com.example.crossingschedule.domain.core.Either
import com.example.crossingschedule.domain.core.Failure
import com.example.crossingschedule.domain.model.CrossingTodo
import com.example.crossingschedule.domain.repository.ActivitiesRepository
import com.example.crossingschedule.presentation.util.IStringProvider
import javax.inject.Inject

class CreateNewTodoItem @Inject constructor(
    private val repository: ActivitiesRepository,
    private val stringProvider: IStringProvider
) {

    suspend operator fun invoke(
        currentList: List<CrossingTodo>,
        newTodoItemMessage: String
    ): Either<Failure, Unit> {
        if (newTodoItemMessage.isBlank()) {
            return Either.Left(
                Failure.ValidationFailure(
                    stringProvider.getString(R.string.error_cannot_be_empty)
                )
            )
        }

        val newTodoItem = CrossingTodo(newTodoItemMessage)
        val updatedList = currentList.plus(newTodoItem)

        return repository.updateCrossingTodoItems(updatedList)
    }
}