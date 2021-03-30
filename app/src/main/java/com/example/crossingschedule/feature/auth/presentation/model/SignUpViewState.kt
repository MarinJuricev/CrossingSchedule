package com.example.crossingschedule.feature.auth.presentation.model

data class SignUpViewState(
    val isLoading: Boolean = false,
    val navigateToSchedule: Boolean = false,
    val signUpError: SignUpError? = null,//TODO double check if this is really the API I want to expose
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val username: String = "",
)

sealed class SignUpError(val error: String) {
    data class UserNameError(val userNameError: String) : SignUpError(userNameError)
    data class EmailError(val emailError: String) : SignUpError(emailError)
    data class PasswordError(val passwordError: String) : SignUpError(passwordError)
    data class ConfirmPasswordError(val passwordError: String) : SignUpError(passwordError)
    data class GeneralError(val errorMessage: String) : SignUpError(errorMessage)
}