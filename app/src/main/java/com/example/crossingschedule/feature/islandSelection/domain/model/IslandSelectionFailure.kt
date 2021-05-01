package com.example.crossingschedule.feature.islandSelection.domain.model

sealed class IslandSelectionFailure(val errorMessage: String) {
    data class RemoteFailure(val error: String) : IslandSelectionFailure(error)
}
