package com.example.crossingschedule.core.util

sealed class Failure(val errorMessage: String) {
    data class RemoteFailure(val error: String) : Failure(error)
    data class ValidationFailure(val error: String) : Failure(error)
}