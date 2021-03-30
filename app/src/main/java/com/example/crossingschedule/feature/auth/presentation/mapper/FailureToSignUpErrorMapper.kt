package com.example.crossingschedule.feature.auth.presentation.mapper

import com.example.crossingschedule.core.model.AuthFailure
import com.example.crossingschedule.core.model.AuthFailure.*
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.auth.presentation.model.SignUpError
import com.example.crossingschedule.feature.auth.presentation.model.SignUpError.*
import javax.inject.Inject

class FailureToSignUpErrorMapper @Inject constructor(
) : Mapper<SignUpError, AuthFailure> {
    override fun map(origin: AuthFailure): SignUpError =
        when (origin) {
            is UserNameAuthFailure -> UserNameError(origin.errorMessage)
            is EmailValidationAuthFailure -> EmailError(origin.errorMessage)
            is PasswordValidationAuthFailure -> PasswordError(origin.errorMessage)
            is ConfirmPasswordValidationAuthFailure -> ConfirmPasswordError(origin.errorMessage)
            else -> GeneralError(origin.errorMessage)
        }
}