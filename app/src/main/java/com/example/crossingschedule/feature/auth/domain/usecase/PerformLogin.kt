package com.example.crossingschedule.feature.auth.domain.usecase

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.Failure
import com.example.crossingschedule.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject


class PerformLogin @Inject constructor(
    private val repository: AuthRepository,
    private val loginValidator: LoginValidator
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Either<Failure, Unit> {
        val validationResult = loginValidator.validate(email, password)
        if(validationResult is Either.Left){
            return validationResult
        }

        return repository.login(email, password)
    }
}