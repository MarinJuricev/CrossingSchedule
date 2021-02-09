package com.example.crossingschedule.feature.login.domain.usecase

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import javax.inject.Inject

private const val EMAIL_REGEX =
    "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
private const val MINIMAL_PASSWORD_LENGTH = 8

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