package com.example.crossingschedule.feature.auth.domain.usecase

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.Either.Left
import com.example.crossingschedule.core.model.Failure
import com.example.crossingschedule.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class CreateAccount @Inject constructor(
    private val signUpValidator: SignUpValidator,
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        email: String,
        username: String,
        password: String,
        confirmPassword: String,
    ): Either<Failure, Unit> {
        val validationResult = signUpValidator.validate(email, password, confirmPassword)
        if (validationResult is Left) {
            return validationResult
        }

        return authRepository.createAccount(email, password, username)
    }
}