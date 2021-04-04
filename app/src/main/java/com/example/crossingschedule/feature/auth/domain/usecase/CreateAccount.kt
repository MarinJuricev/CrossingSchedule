package com.example.crossingschedule.feature.auth.domain.usecase

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.Either.Left
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure
import com.example.crossingschedule.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class CreateAccount @Inject constructor(
    private val signUpValidator: SignUpValidator,
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
    ): Either<AuthFailure, Unit> {
        val validationResult = signUpValidator.validate(username, email, password, confirmPassword)
        if (validationResult is Left) {
            return validationResult
        }

        return authRepository.createAccount(username, email, password)
    }
}