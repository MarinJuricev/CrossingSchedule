package com.example.crossingschedule.feature.auth.data.repository

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.EncryptedPrefsService
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.auth.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val authProvider: AuthProvider,
    private val loginApiService: LoginApiService,
    private val encryptedPrefsService: EncryptedPrefsService,
    private val loginResponseToEitherMapper: Mapper<Either<Failure, Unit>, String>
) : LoginRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Either<Failure, Unit> = when (val loginResult = authProvider.login(email, password)) {
        is Either.Right -> {
            encryptedPrefsService.saveValue(AUTH_TOKEN_KEY, authProvider.getUserIdToken())
            loginResponseToEitherMapper.map(loginApiService.authenticateUser())
        }
        is Either.Left -> loginResult
    }
}