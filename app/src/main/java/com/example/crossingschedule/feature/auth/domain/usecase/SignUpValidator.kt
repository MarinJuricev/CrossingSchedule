package com.example.crossingschedule.feature.auth.domain.usecase

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.Failure
import com.example.crossingschedule.feature.auth.domain.EMAIL_REGEX
import com.example.crossingschedule.feature.auth.domain.MINIMAL_PASSWORD_LENGTH
import javax.inject.Inject

class SignUpValidator @Inject constructor(){

    fun validate(
        email: String,
        password: String,
        confirmPassword: String
    ): Either<Failure, Unit> {
        return when{
            !email.matches(EMAIL_REGEX.toRegex()) -> {
                Either.Left(Failure.EmailValidationFailure("Invalid e-mail provided"))
            }
            password.isBlank() || password.length <= MINIMAL_PASSWORD_LENGTH -> {
                Either.Left(Failure.PasswordValidationFailure("Password must have at least 8 chars"))
            }
            confirmPassword.isBlank() ||
            password.length <= MINIMAL_PASSWORD_LENGTH ||
            confirmPassword != password
            -> {
                Either.Left(Failure.ConfirmPasswordValidationFailure("The passwords do not match"))
            }
            else -> Either.Right(Unit)
        }
    }
}