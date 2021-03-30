package com.example.crossingschedule.feature.auth.domain.model

// TODO: 31. 03. 2021. Refactor other usages, we should only create feature specific Failures, not global ones
sealed class AuthFailure(val errorMessage: String) {
    data class RemoteAuthFailure(val error: String) : AuthFailure(error)
    data class ValidationAuthFailure(val error: String) : AuthFailure(error)
    data class UserNameAuthFailure(val error: String) : AuthFailure(error)
    data class EmailValidationAuthFailure(val error: String) : AuthFailure(error)
    data class PasswordValidationAuthFailure(val error: String) : AuthFailure(error)
    data class ConfirmPasswordValidationAuthFailure(val error: String) : AuthFailure(error)
}