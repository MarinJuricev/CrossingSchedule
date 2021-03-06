package com.example.crossingschedule.core.ext

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.buildLeft
import com.example.crossingschedule.core.model.buildRight

suspend fun <T> safeApiCall(
    fallbackErrorMessage: String,
    apiCall: suspend () -> T,
): Either<String, T> {
    return try {
        apiCall.invoke().buildRight()
    } catch (throwable: Throwable) {
        (throwable.localizedMessage ?: fallbackErrorMessage).buildLeft()
    }
}