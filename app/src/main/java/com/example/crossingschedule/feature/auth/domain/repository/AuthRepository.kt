package com.example.crossingschedule.feature.auth.domain.repository

import com.example.crossingschedule.feature.auth.domain.model.AuthFailure
import com.example.crossingschedule.core.model.Either

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String
    ): Either<AuthFailure, Unit>

    suspend fun createAccount(
        username: String,
        email: String,
        password: String,
    ): Either<AuthFailure, Unit>
}