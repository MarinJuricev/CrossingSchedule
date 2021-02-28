package com.example.crossingschedule.feature.auth.domain.repository

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure

interface LoginRepository {
    suspend fun login(email: String, password: String): Either<Failure, Unit>
}