package com.example.crossingschedule.feature.schedule.domain.usecase

import app.cash.turbine.test
import com.example.crossingschedule.core.util.DateHandler
import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

private const val ERROR_MESSAGE = "ERROR_MESSAGE"
private const val YEAR = 2021
private const val MONTH = 1
private const val DAY = 1
private const val FORMATTED_DATE = "FORMATTED_DATE"

@ExperimentalCoroutinesApi
@ExperimentalTime
class GetActivitiesForDayTest {

    private val activitiesRepository: ActivitiesRepository = mockk()

    private val dateHandler: DateHandler = mockk()

    private lateinit var sut: GetActivitiesForDay

    @Before
    fun setUp() {
        sut = GetActivitiesForDay(
            dateHandler,
            activitiesRepository,
        )
    }

    @Test
    fun `getActivitiesForDay should trigger getActivitiesFoSpecifiedDay`() = runBlockingTest {
        val failure = Either.Left(Failure.RemoteFailure(ERROR_MESSAGE))
        val repositoryResult = flow {
            emit(failure)
        }

        coEvery {
            dateHandler.formatYearDayMonthToDesiredFormat(
                YEAR,
                MONTH,
                DAY
            )
        } coAnswers {
            FORMATTED_DATE
        }

        coEvery {
            activitiesRepository.getActivitiesFoSpecifiedDay(FORMATTED_DATE)
        } coAnswers {
            repositoryResult
        }

        val actualResult = sut(YEAR, MONTH, DAY)
        actualResult.test {
            assert(failure == expectItem())
            expectComplete()
        }

        coVerify { activitiesRepository.getActivitiesFoSpecifiedDay(FORMATTED_DATE) }
    }
}