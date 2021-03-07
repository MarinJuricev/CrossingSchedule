package com.example.crossingschedule.feature.auth.data.mapper

import com.example.crossingschedule.core.model.CrossingStatus
import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.Failure
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.auth.data.model.AuthenticateUserResponse
import javax.inject.Inject

class AuthenticateUserResponseToEitherMapper @Inject constructor(
) : Mapper<Either<Failure, Unit>, AuthenticateUserResponse> {

    override fun map(origin: AuthenticateUserResponse): Either<Failure, Unit> {
        return if (origin.status == CrossingStatus.Success) {
            Either.Right(Unit)
        } else {
            Either.Left(Failure.RemoteFailure(origin.message ?: "Unknown Error Occurred"))
        }
    }
}