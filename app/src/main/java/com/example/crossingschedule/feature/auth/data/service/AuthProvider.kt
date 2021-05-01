package com.example.crossingschedule.feature.auth.data.service

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure

interface AuthProvider {
    suspend fun login(email: String, password: String): Either<AuthFailure, Unit>
    suspend fun getUserIdToken(): String
    suspend fun createAccount(email: String, password: String): Either<AuthFailure, Unit>
}