package com.example.crossingschedule.feature.auth.data.mapper

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.core.util.Mapper
import javax.inject.Inject

class TokenResponseToEitherMapper @Inject constructor(
) : Mapper<Either<Failure, Unit>, String> {

    override fun map(origin: String): Either<Failure, Unit> {
        return Either.Right(Unit)
//        return if (origin.isSuccessful) {
//            Either.Right(Unit)
//        } else {
//            Either.Left(Failure.RemoteFailure(origin.message()))
//        }
    }
}