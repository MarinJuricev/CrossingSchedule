package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.R
import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.core.util.IStringProvider
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val ERROR_CANNOT_BE_EMPTY = "ERROR_CANNOT_BE_EMPTY"

@ExperimentalCoroutinesApi
class CreateNewTodoItemTest {

    val activitiesRepository: ActivitiesRepository = mockk()
    val stringProvider: IStringProvider = mockk()

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

            val actualResult = sut(null, "")
            val expectedResult = Either.Left(
                Failure.ValidationFailure(
                    ERROR_CANNOT_BE_EMPTY
                )
            )

            assert(actualResult == expectedResult)
        }
}