package com.example.crossingschedule.feature.login.domain.usecase

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.feature.login.domain.repository.LoginRepository
import javax.inject.Inject


class LoginClicked @Inject constructor(
    private val repository: LoginRepository,
    private val loginValidator: LoginValidator
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Either<Failure, Unit> {
        return loginValidator.validate(email, password)
    }
}