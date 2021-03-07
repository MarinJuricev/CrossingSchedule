package com.example.crossingschedule.feature.auth.data.model

data class AuthenticateUserResponse(
    val status: String,
    val data: AuthResponse?,
    val message: String?
)

data class AuthResponse(
    val message: String
)