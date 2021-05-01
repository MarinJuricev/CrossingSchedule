package com.example.crossingschedule.feature.auth.data.mapper

import com.example.crossingschedule.core.model.CrossingResponse
import com.example.crossingschedule.core.model.CrossingStatus
import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.Either.Left
import com.example.crossingschedule.core.model.Either.Right
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.auth.data.model.AuthResponse
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure
import javax.inject.Inject

class AuthResponseToEitherMapper @Inject constructor(
) : Mapper<Either<AuthFailure, Unit>, CrossingResponse<AuthResponse>> {

    override suspend fun map(origin: CrossingResponse<AuthResponse>): Either<AuthFailure, Unit> {
        return if (origin.status == CrossingStatus.Success) {
            Right(Unit)
        } else {
            Left(AuthFailure.RemoteAuthFailure(origin.message ?: "Unknown Error Occurred"))
        }
    }
}