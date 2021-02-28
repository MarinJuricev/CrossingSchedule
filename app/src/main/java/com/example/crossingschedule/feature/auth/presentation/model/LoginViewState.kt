package com.example.crossingschedule.feature.auth.presentation.model

data class LoginViewState(
    val isLoading: Boolean = false,
    val navigateToSchedule: Boolean = false,
    val loginError: LoginError? = null,//TODO double check if this is really the API I want to expose
    val email: String = "",
    val password: String = "",
)

sealed class LoginError(val error: String) {
    data class PasswordError(val passwordError: String) : LoginError(passwordError)
    data class EmailError(val emailError: String) : LoginError(emailError)
    data class GeneralError(val errorMessage: String) : LoginError(errorMessage)
}