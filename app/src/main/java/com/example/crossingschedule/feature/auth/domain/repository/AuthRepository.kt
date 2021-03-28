package com.example.crossingschedule.feature.auth.domain.repository

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.Failure

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String
    ): Either<Failure, Unit>

    suspend fun createAccount(
        email: String,
        password: String,
        username: String,
    ): Either<Failure, Unit>
}