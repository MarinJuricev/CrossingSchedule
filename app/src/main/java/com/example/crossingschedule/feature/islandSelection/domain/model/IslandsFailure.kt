package com.example.crossingschedule.feature.islandSelection.domain.model

sealed class IslandsFailure(val errorMessage: String) {
    data class RemoteFailure(val error: String) : IslandsFailure(error)
}
