package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.R
import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.AuthFailure
import com.example.crossingschedule.core.util.IStringProvider
import com.example.crossingschedule.feature.schedule.domain.model.CrossingTodo
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val ERROR_CANNOT_BE_EMPTY = "ERROR_CANNOT_BE_EMPTY"
private const val NEW_TODO = "NEW_TODO"
private const val NEW_TODO_2 = "NEW_TODO_2"

@ExperimentalCoroutinesApi
class CreateNewTodoItemTest {

    private val activitiesRepository: ActivitiesRepository = mockk()
    private val stringProvider: IStringProvider = mockk()

    lateinit var sut: CreateNewTodoItem

    @Before
    fun setUp() {
        sut = CreateNewTodoItem(
            activitiesRepository,
            stringProvider
        )
    }

    @Test
    fun `createNewTodoItem should return an error when the provided message is blank`() =
        runBlockingTest {
            every { stringProvider.getString(R.string.error_cannot_be_empty) } answers {
                ERROR_CANNOT_BE_EMPTY
            }

            val actualResult = sut(listOf(), "", "")
            val expectedResult = Either.Left(
                AuthFailure.ValidationAuthFailure(
                    ERROR_CANNOT_BE_EMPTY
                )
            )

            assert(actualResult == expectedResult)
        }

    @Test
    fun `createNewTodoItem should trigger repository updateCrossingTodoItems with newly generatedList when the provided list is empty`() =
        runBlockingTest {
            val expectedList = listOf(CrossingTodo(NEW_TODO))
            coEvery { activitiesRepository.updateCrossingTodoItems(expectedList, "") } coAnswers {
                Either.Right(Unit)
            }

            val actualResult = sut(listOf(), "", NEW_TODO)
            val expectedResult = Either.Right(Unit)

            assert(actualResult == expectedResult)
        }

    @Test
    fun `createNewTodoItem should trigger repository updateCrossingTodoItems with newly generatedList when the provided list is is not empty`() =
        runBlockingTest {
            val expectedList = listOf(
                CrossingTodo(NEW_TODO),
                CrossingTodo(NEW_TODO_2)
            )
            coEvery { activitiesRepository.updateCrossingTodoItems(expectedList, "") } coAnswers {
                Either.Right(Unit)
            }

            val actualResult = sut(
                listOf(CrossingTodo(NEW_TODO)),
                currentDate = "",
                NEW_TODO_2
            )
            val expectedResult = Either.Right(Unit)

            assert(actualResult == expectedResult)
        }
}