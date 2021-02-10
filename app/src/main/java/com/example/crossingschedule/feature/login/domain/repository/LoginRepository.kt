package com.example.crossingschedule.feature.login.domain.repository

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure

interface LoginRepository {
    suspend fun createAccount(email: String, password: String): Either<Failure, Unit>
}