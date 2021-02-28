package com.example.crossingschedule.feature.auth.domain.usecase

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.feature.auth.domain.repository.LoginRepository
import javax.inject.Inject


class PerformLogin @Inject constructor(
    private val repository: LoginRepository,
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