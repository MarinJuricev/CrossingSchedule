package com.example.crossingschedule.feature.auth.data.mapper

import com.example.crossingschedule.R
import com.example.crossingschedule.core.model.CrossingResponse
import com.example.crossingschedule.core.model.CrossingStatus
import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.Either.Right
import com.example.crossingschedule.core.model.buildLeft
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.core.util.StringProvider
import com.example.crossingschedule.feature.auth.data.model.AuthResponse
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure.RemoteAuthFailure
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AuthResponseToEitherMapperTest {

    private val stringProvider: StringProvider = mockk()

    private lateinit var sut: Mapper<Either<AuthFailure, Unit>, CrossingResponse<AuthResponse>>

    @Before
    fun setUp() {
        sut = AuthResponseToEitherMapper(
            stringProvider
        )
    }

    @Test
    fun `map should return Right when the origin status is CrossingStatus Success`() =
        runBlockingTest {
            val successResponse = CrossingResponse<AuthResponse>(
                status = CrossingStatus.Success,
                null,
                null,
            )

            val actualResult = sut.map(successResponse)
            val expectedResult = Right(Unit)

            assert(actualResult == expectedResult)
        }

    @Test
    fun `map should return Left when the origin status is CrossingStatus Fail`() = runBlockingTest {
        val errorMessage = "errorMessage"
        val failResponse = CrossingResponse<AuthResponse>(
            status = CrossingStatus.Fail,
            null,
            errorMessage,
        )

        val actualResult = sut.map(failResponse)
        val expectedResult = RemoteAuthFailure(errorMessage).buildLeft()

        assert(actualResult == expectedResult)
    }

    @Test
    fun `map should return Left with default message when the origin status is CrossingStatus Fail and message is null`() =
        runBlockingTest {
            val failResponse = CrossingResponse<AuthResponse>(
                status = CrossingStatus.Fail,
                null,
                null,
            )
            val errorMessage = "Unknown Error Occurred"

            every {
                stringProvider.getString(R.string.generic_error_message)
            } answers { errorMessage }

            val actualResult = sut.map(failResponse)
            val expectedResult = RemoteAuthFailure(errorMessage).buildLeft()

            assert(actualResult == expectedResult)
        }
}