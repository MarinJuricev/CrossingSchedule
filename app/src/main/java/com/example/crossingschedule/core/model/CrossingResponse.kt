package com.example.crossingschedule.core.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CrossingResponse<T>(
    val status: CrossingStatus,
    val data: T? = null,
    val message: String? = null,
)
