package com.example.crossingschedule.feature.auth.data.mapper

import com.example.crossingschedule.core.model.CrossingStatus
import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.auth.data.model.LoginUserResponse
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginUserResponseToEitherMapperTest {

    private lateinit var sut: Mapper<Either<AuthFailure, Unit>, LoginUserResponse>

    @Before
    fun setUp() {
        sut = AuthenticateUserResponseToEitherMapper()
    }

    @Test
    fun `map should return Right when the origin status is CrossingStatus Success`() =
        runBlockingTest {
            val successResponse = LoginUserResponse(
                status = CrossingStatus.Success,
                null,
                null,
            )

            val actualResult = sut.map(successResponse)
            val expectedResult = Either.Right(Unit)

            assert(actualResult == expectedResult)
        }

    @Test
    fun `map should return Left when the origin status is CrossingStatus Fail`() = runBlockingTest {
        val errorMessage = "errorMessage"
        val failResponse = LoginUserResponse(
            status = CrossingStatus.Fail,
            null,
            errorMessage,
        )

        val actualResult = sut.map(failResponse)
        val expectedResult = Either.Left(AuthFailure.RemoteAuthFailure(errorMessage))

        assert(actualResult == expectedResult)
    }

    @Test
    fun `map should return Left with default message when the origin status is CrossingStatus Fail and message is null`() =
        runBlockingTest {
            val failResponse = LoginUserResponse(
                status = CrossingStatus.Fail,
                null,
                null,
            )

            val actualResult = sut.map(failResponse)
            val expectedResult =
                Either.Left(AuthFailure.RemoteAuthFailure("Unknown Error Occurred"))

            assert(actualResult == expectedResult)
        }
}