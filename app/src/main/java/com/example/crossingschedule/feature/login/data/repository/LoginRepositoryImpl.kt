package com.example.crossingschedule.feature.login.data.repository

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.feature.login.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : LoginRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Either<Failure, Unit> = authApi.login(email, password)
}