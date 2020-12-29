package com.example.crossingschedule.domain.core

sealed class Failure {
    data class RemoteFailure(val errorMessage: String) : Failure()
}