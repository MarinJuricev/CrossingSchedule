package com.example.crossingschedule.feature.auth.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponse(
    val id: String,
    val username: String,
)