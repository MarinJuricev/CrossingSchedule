package com.example.crossingschedule.feature.auth.presentation.mapper

import com.example.crossingschedule.core.model.Failure
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.auth.presentation.model.SignUpError
import javax.inject.Inject

class FailureToSignUpErrorMapper @Inject constructor(
) : Mapper<SignUpError, Failure> {
    override fun map(origin: Failure): SignUpError =
        when (origin) {
            is Failure.EmailValidationFailure -> SignUpError.EmailError(origin.errorMessage)
            is Failure.PasswordValidationFailure -> SignUpError.PasswordError(origin.errorMessage)
            is Failure.ConfirmPasswordValidationFailure -> SignUpError.ConfirmPasswordError(origin.errorMessage)
            else -> SignUpError.GeneralError(origin.errorMessage)
        }
}