package com.example.crossingschedule.feature.auth.data.repository

import com.example.crossingschedule.core.di.AUTH_TOKEN_KEY
import com.example.crossingschedule.core.model.CrossingResponse
import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.util.EncryptedPrefsService
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.auth.data.model.AuthResponse
import com.example.crossingschedule.feature.auth.data.model.CreateAccountBody
import com.example.crossingschedule.feature.auth.data.service.AuthApiService
import com.example.crossingschedule.feature.auth.data.service.AuthProvider
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure
import com.example.crossingschedule.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authProvider: AuthProvider,
    private val authApiService: AuthApiService,
    private val encryptedPrefsService: EncryptedPrefsService,
    private val loginResponseToEitherMapper: Mapper<Either<AuthFailure, Unit>, CrossingResponse<AuthResponse>>
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Either<AuthFailure, Unit> =
        when (val result = authProvider.login(email, password)) {
            is Either.Right -> {
                encryptedPrefsService.saveValue(AUTH_TOKEN_KEY, authProvider.getUserIdToken())
                loginResponseToEitherMapper.map(authApiService.loginUser())
            }
            is Either.Left -> result
        }

    override suspend fun createAccount(
        username: String,
        email: String,
        password: String,
    ): Either<AuthFailure, Unit> =
        when (val result = authProvider.createAccount(email, password)) {
            is Either.Right -> {
                encryptedPrefsService.saveValue(AUTH_TOKEN_KEY, authProvider.getUserIdToken())
                loginResponseToEitherMapper.map(
                    authApiService.createAccount(CreateAccountBody(username))
                )
            }
            is Either.Left -> result
        }
}
