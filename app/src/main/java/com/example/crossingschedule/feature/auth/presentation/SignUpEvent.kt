package com.example.crossingschedule.feature.auth.presentation

sealed class SignUpEvent {
    data class OnEmailChange(val newEmail: String) : SignUpEvent()
    data class OnUsernameChange(val newUsername: String) : SignUpEvent()
    data class OnPasswordChange(val newPassword: String) : SignUpEvent()
    data class OnConfirmPasswordChange(val newConfirmPassword: String) : SignUpEvent()
    object OnCreateAccountClick : SignUpEvent()
}
