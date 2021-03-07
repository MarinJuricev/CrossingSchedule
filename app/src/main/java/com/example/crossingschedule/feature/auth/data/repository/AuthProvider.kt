package com.example.crossingschedule.feature.auth.data.repository

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.Failure

interface AuthProvider {
    suspend fun login(email: String, password: String): Either<Failure, Unit>
    suspend fun getUserIdToken(): String
    suspend fun createAccount(email: String, password: String): Either<Failure, Unit>
}