package com.example.crossingschedule.feature.auth.presentation.mapper

import com.example.crossingschedule.core.model.AuthFailure
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.auth.presentation.model.LoginError
import javax.inject.Inject

class FailureToLoginErrorMapper @Inject constructor(
) : Mapper<LoginError, AuthFailure> {
    override fun map(origin: AuthFailure): LoginError =
        when (origin) {
            is AuthFailure.EmailValidationAuthFailure -> LoginError.EmailError(origin.errorMessage)
            is AuthFailure.PasswordValidationAuthFailure -> LoginError.PasswordError(origin.errorMessage)
            else -> LoginError.GeneralError(origin.errorMessage)
        }
}