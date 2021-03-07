package com.example.crossingschedule.feature.auth.data.repository

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.EncryptedPrefsService
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authProvider: AuthProvider,
    private val authApiService: AuthApiService,
    private val encryptedPrefsService: EncryptedPrefsService,
    private val loginResponseToEitherMapper: Mapper<Either<Failure, Unit>, String>
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Either<Failure, Unit> =
        authProvider.login(email, password).handleAuthProviderResponse()

    override suspend fun createAccount(
        email: String,
        password: String,
    ): Either<Failure, Unit> =
        authProvider.createAccount(email, password).handleAuthProviderResponse()

    private suspend fun Either<Failure, Unit>.handleAuthProviderResponse(): Either<Failure, Unit> {
        return when (this) {
            is Either.Right -> {
                encryptedPrefsService.saveValue(AUTH_TOKEN_KEY, authProvider.getUserIdToken())
                loginResponseToEitherMapper.map(authApiService.authenticateUser())
            }
            is Either.Left -> this
        }
    }
}