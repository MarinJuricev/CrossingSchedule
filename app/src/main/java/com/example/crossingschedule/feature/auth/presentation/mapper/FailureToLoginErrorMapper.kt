package com.example.crossingschedule.feature.auth.presentation.mapper

import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.auth.presentation.model.LoginError
import javax.inject.Inject

class FailureToLoginErrorMapper @Inject constructor(
) : Mapper<LoginError, Failure> {
    override fun map(origin: Failure): LoginError =
        when (origin) {
            is Failure.EmailValidationFailure -> LoginError.EmailError(origin.errorMessage)
            is Failure.PasswordValidationFailure -> LoginError.PasswordError(origin.errorMessage)
            else -> LoginError.GeneralError(origin.errorMessage)
        }
}