package com.example.crossingschedule.feature.islands.domain.model

import com.example.crossingschedule.feature.auth.domain.model.AuthFailure

sealed class IslandsFailure(val errorMessage: String) {
    data class RemoteFailure(val error: String) : IslandsFailure(error)
}
