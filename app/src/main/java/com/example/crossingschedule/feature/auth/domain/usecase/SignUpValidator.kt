package com.example.crossingschedule.feature.auth.domain.usecase

import com.example.crossingschedule.feature.auth.domain.model.AuthFailure
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure.*
import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.buildLeft
import com.example.crossingschedule.core.model.buildRight
import com.example.crossingschedule.feature.auth.domain.EMAIL_REGEX
import com.example.crossingschedule.feature.auth.domain.MINIMAL_PASSWORD_LENGTH
import javax.inject.Inject

class SignUpValidator @Inject constructor() {

    fun validate(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Either<AuthFailure, Unit> {
        return when {
            username.isBlank() -> UserNameAuthFailure("Username can't be empty").buildLeft()
            !email.matches(EMAIL_REGEX.toRegex()) ->
                EmailValidationAuthFailure("Invalid e-mail provided").buildLeft()
            password.isBlank() || password.length <= MINIMAL_PASSWORD_LENGTH ->
                PasswordValidationAuthFailure("Password must have at least 8 chars").buildLeft()
            confirmPassword.isBlank() ||
                    password.length <= MINIMAL_PASSWORD_LENGTH ||
                    confirmPassword != password
            -> ConfirmPasswordValidationAuthFailure("The passwords do not match").buildLeft()
            else -> Unit.buildRight()
        }
    }
}