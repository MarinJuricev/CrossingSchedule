package com.example.crossingschedule.feature.auth.domain.repository

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String
    ): Either<Failure, Unit>
    suspend fun createAccount(
        email: String,
        password: String,
        confirmPassword: String
    ): Either<Failure, Unit>
}