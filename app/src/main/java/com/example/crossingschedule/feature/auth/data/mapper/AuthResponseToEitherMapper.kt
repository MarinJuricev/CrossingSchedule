package com.example.crossingschedule.feature.auth.data.mapper

import com.example.crossingschedule.R
import com.example.crossingschedule.core.model.*
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.core.util.StringProvider
import com.example.crossingschedule.feature.auth.data.model.AuthResponse
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure.RemoteAuthFailure
import javax.inject.Inject

class AuthResponseToEitherMapper @Inject constructor(
    private val stringProvider: StringProvider
) : Mapper<Either<AuthFailure, Unit>, CrossingResponse<AuthResponse>> {

    override suspend fun map(origin: CrossingResponse<AuthResponse>): Either<AuthFailure, Unit> {
        return if (origin.status == CrossingStatus.Success) {
            Unit.buildRight()
        } else {
            RemoteAuthFailure(
                origin.message ?: stringProvider.getString(R.string.generic_error_message)
            ).buildLeft()
        }
    }
}