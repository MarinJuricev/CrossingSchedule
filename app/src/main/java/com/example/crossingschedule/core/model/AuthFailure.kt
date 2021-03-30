package com.example.crossingschedule.core.model

sealed class AuthFailure(val errorMessage: String) {
    data class RemoteAuthFailure(val error: String) : AuthFailure(error)
    data class ValidationAuthFailure(val error: String) : AuthFailure(error)
    data class UserNameAuthFailure(val error: String) : AuthFailure(error)
    data class EmailValidationAuthFailure(val error: String) : AuthFailure(error)
    data class PasswordValidationAuthFailure(val error: String) : AuthFailure(error)
    data class ConfirmPasswordValidationAuthFailure(val error: String) : AuthFailure(error)
}