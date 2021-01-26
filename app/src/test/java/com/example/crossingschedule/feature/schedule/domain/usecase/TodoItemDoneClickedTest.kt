package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.feature.schedule.domain.model.CrossingTodo
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

private const val TODO_ITEM = "TODO_ITEM"

@ExperimentalCoroutinesApi
class TodoItemDoneClickedTest {

    private val activitiesRepository: ActivitiesRepository = mockk()

    lateinit var sut: TodoItemDoneClicked

    @Before
    fun setUp() {
        sut = TodoItemDoneClicked(activitiesRepository)
    }

    @Test
    fun `todoItemDoneClicked should trigger repository updateCrossingTodoItems with newly generatedList when the provided list is null`() =
        runBlockingTest {
            val generatedList = listOf(CrossingTodo(TODO_ITEM))
            coEvery { activitiesRepository.updateCrossingTodoItems(generatedList) } coAnswers {
                Either.Right(Unit)
            }

            val actualResult = sut(null, CrossingTodo(TODO_ITEM))
            val expectedResult = Either.Right(Unit)

            assert(actualResult == expectedResult)
        }

    @Test
    fun `todoItemDoneClicked should trigger repository updateCrossingTodoItems with newly generatedList when the provided list is empty`() =
        runBlockingTest {
            val generatedList = listOf(CrossingTodo(TODO_ITEM))
            coEvery { activitiesRepository.updateCrossingTodoItems(generatedList) } coAnswers {
                Either.Right(Unit)
            }

            val actualResult = sut(listOf(), CrossingTodo(TODO_ITEM))
            val expectedResult = Either.Right(Unit)

            assert(actualResult == expectedResult)
        }

    @Test
    fun `todoItemDoneClicked should trigger repository updateCrossingTodoItems with newly generatedList with the updated isDone value when the todo is present in the list`() =
        runBlockingTest {
            val generatedList = listOf(CrossingTodo(TODO_ITEM, isDone = true))
            coEvery { activitiesRepository.updateCrossingTodoItems(generatedList) } coAnswers {
                Either.Right(Unit)
            }

            val actualResult = sut(listOf(CrossingTodo(TODO_ITEM)), CrossingTodo(TODO_ITEM))
            val expectedResult = Either.Right(Unit)

            assert(actualResult == expectedResult)
        }
}