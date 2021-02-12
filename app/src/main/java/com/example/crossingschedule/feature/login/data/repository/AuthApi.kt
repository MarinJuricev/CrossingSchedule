package com.example.crossingschedule.feature.login.data.repository

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure

interface AuthApi {
    suspend fun login(email: String, password: String): Either<Failure, Unit>
}