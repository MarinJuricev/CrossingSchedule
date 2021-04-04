package com.example.crossingschedule.feature.auth.data.mapper

import com.example.crossingschedule.core.model.CrossingStatus
import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.auth.data.model.LoginUserResponse
import javax.inject.Inject

class AuthenticateUserResponseToEitherMapper @Inject constructor(
) : Mapper<Either<AuthFailure, Unit>, LoginUserResponse> {

    override suspend fun map(origin: LoginUserResponse): Either<AuthFailure, Unit> {
        return if (origin.status == CrossingStatus.Success) {
            Either.Right(Unit)
        } else {
            Either.Left(AuthFailure.RemoteAuthFailure(origin.message ?: "Unknown Error Occurred"))
        }
    }
}