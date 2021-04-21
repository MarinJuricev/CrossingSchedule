package com.example.crossingschedule.feature.islandCreation.domain.model

sealed class IslandCreationFailure(val errorMessage: String) {
    data class RemoteFailure(val error: String) : IslandCreationFailure(error)
    data class InvalidIslandName(val error: String) : IslandCreationFailure(error)
    data class InvalidHemisphere(val error: String) : IslandCreationFailure(error)
    data class InvalidNumberOfVillagers(val error: String) : IslandCreationFailure(error)
}
