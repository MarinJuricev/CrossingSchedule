package com.example.crossingschedule.feature.login.data.repository

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure

interface AuthProvider {
    suspend fun login(email: String, password: String): Either<Failure, Unit>
    suspend fun getUserIdToken(): String
}