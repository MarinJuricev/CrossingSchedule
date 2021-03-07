package com.example.crossingschedule.feature.auth.domain.usecase

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.Failure
import com.example.crossingschedule.feature.auth.domain.EMAIL_REGEX
import com.example.crossingschedule.feature.auth.domain.MINIMAL_PASSWORD_LENGTH
import javax.inject.Inject

class LoginValidator @Inject constructor() {

    fun validate(
        email: String,
        password: String
    ): Either<Failure, Unit> {
        //TODO Add the String provider, don't hard code one locale
        return when {
            !email.matches(EMAIL_REGEX.toRegex()) -> {
                Either.Left(Failure.EmailValidationFailure("Invalid e-mail provided"))
            }
            password.isBlank() || password.length <= MINIMAL_PASSWORD_LENGTH -> {
                Either.Left(Failure.PasswordValidationFailure("Password must have at least 8 chars"))
            }
            else -> Either.Right(Unit)
        }
    }
}