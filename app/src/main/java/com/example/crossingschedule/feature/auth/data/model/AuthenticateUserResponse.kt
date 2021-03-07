package com.example.crossingschedule.feature.auth.data.model

import com.example.crossingschedule.core.model.CrossingStatus
import com.squareup.moshi.JsonClass

//TODO Make a generic factory that can build this jsend blueprint
@JsonClass(generateAdapter = true)
data class AuthenticateUserResponse(
    val status: CrossingStatus,
    val data: AuthResponse?,
    val message: String?
)

@JsonClass(generateAdapter = true)
data class AuthResponse(
    val message: String
)