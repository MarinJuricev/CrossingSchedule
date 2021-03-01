package com.example.crossingschedule.feature.auth.domain.usecase

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class CreateAccount @Inject constructor(
    private val signUpValidator: SignUpValidator,
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        confirmPassword: String
    ): Either<Failure, Unit> {
        val validationResult = signUpValidator.validate(email, password, confirmPassword)
        if (validationResult is Either.Left) {
            return validationResult
        }

        return authRepository.createAccount(email, password)
    }
}