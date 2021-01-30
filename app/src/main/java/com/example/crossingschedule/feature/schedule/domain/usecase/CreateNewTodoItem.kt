package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.R
import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.feature.schedule.domain.model.CrossingTodo
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import com.example.crossingschedule.core.util.IStringProvider
import javax.inject.Inject

class CreateNewTodoItem @Inject constructor(
    private val repository: ActivitiesRepository,
    private val stringProvider: IStringProvider
) {

    suspend operator fun invoke(
        currentList: List<CrossingTodo>,
        currentDate: String,
        newTodoItemMessage: String
    ): Either<Failure, Unit> {
        if (newTodoItemMessage.isBlank()) {
            return buildError()
        }

        val newTodoItem = CrossingTodo(newTodoItemMessage)
        val listToBeSent = generateListToBeSent(currentList, newTodoItem)

        return repository.updateCrossingTodoItems(listToBeSent, currentDate)
    }

    private fun generateListToBeSent(
        currentList: List<CrossingTodo>,
        newTodoItem: CrossingTodo
    ): List<CrossingTodo> =
        if (currentList.isEmpty()) {
            listOf(newTodoItem)
        } else {
            currentList.plus(newTodoItem)
        }

    private fun buildError(): Either<Failure, Unit> =
        Either.Left(
            Failure.ValidationFailure(
                stringProvider.getString(R.string.error_cannot_be_empty)
            )
        )
}