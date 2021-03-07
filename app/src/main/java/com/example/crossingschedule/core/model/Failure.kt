package com.example.crossingschedule.core.model

//TODO: This approach does not scale separate failures by feature
sealed class Failure(val errorMessage: String) {
    data class RemoteFailure(val error: String) : Failure(error)
    data class ValidationFailure(val error: String) : Failure(error)
    data class EmailValidationFailure(val error: String) : Failure(error)
    data class PasswordValidationFailure(val error: String) : Failure(error)
    data class ConfirmPasswordValidationFailure(val error: String) : Failure(error)
}