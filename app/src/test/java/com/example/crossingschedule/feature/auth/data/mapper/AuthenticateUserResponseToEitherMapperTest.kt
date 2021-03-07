package com.example.crossingschedule.feature.auth.data.mapper

import com.example.crossingschedule.core.model.CrossingStatus
import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.Failure
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.auth.data.model.AuthenticateUserResponse
import org.junit.Before
import org.junit.Test

class AuthenticateUserResponseToEitherMapperTest {

    private lateinit var sut: Mapper<Either<Failure, Unit>, AuthenticateUserResponse>

    @Before
    fun setUp() {
        sut = AuthenticateUserResponseToEitherMapper()
    }

    @Test
    fun `map should return Right when the origin status is CrossingStatus Success`(){
        val successResponse = AuthenticateUserResponse(
            status = CrossingStatus.Success,
            null,
            null,
        )

        val actualResult = sut.map(successResponse)
        val expectedResult = Either.Right(Unit)

        assert(actualResult == expectedResult)
    }

    @Test
    fun `map should return Left when the origin status is CrossingStatus Fail`(){
        val errorMessage = "errorMessage"
        val failResponse = AuthenticateUserResponse(
            status = CrossingStatus.Fail,
            null,
            errorMessage,
        )

        val actualResult = sut.map(failResponse)
        val expectedResult = Either.Left(Failure.RemoteFailure(errorMessage))

        assert(actualResult == expectedResult)
    }

    @Test
    fun `map should return Left with default message when the origin status is CrossingStatus Fail and message is null`(){
        val failResponse = AuthenticateUserResponse(
            status = CrossingStatus.Fail,
            null,
            null,
        )

        val actualResult = sut.map(failResponse)
        val expectedResult = Either.Left(Failure.RemoteFailure("Unknown Error Occurred"))

        assert(actualResult == expectedResult)
    }
}