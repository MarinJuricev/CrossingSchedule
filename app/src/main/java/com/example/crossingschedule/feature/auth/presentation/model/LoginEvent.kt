package com.example.crossingschedule.feature.auth.presentation.model

sealed class LoginEvent {
    data class OnEmailChange(val newEmail: String) : LoginEvent()
    data class OnPasswordChange(val newPassword: String) : LoginEvent()
    object OnLoginClick : LoginEvent()

}
