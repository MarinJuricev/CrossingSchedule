package com.example.crossingschedule.feature.login.data.repository

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.feature.login.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(

) : LoginRepository {

    override suspend fun createAccount(email: String, password: String): Either<Failure, Unit> {
        TODO("Not yet implemented")
    }
}