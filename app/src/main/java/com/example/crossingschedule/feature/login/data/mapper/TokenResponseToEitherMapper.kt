package com.example.crossingschedule.feature.login.data.mapper

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.core.util.Mapper
import retrofit2.Response
import javax.inject.Inject

class TokenResponseToEitherMapper @Inject constructor(
) : Mapper<Either<Failure, Unit>, Response<String>> {

    override fun map(origin: Response<String>): Either<Failure, Unit> {
        return if (origin.isSuccessful) {
            Either.Right(Unit)
        } else {
            Either.Left(Failure.RemoteFailure(origin.message()))
        }
    }
}