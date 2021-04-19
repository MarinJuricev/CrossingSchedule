package com.example.crossingschedule.feature.islandCreation.presentation.model

import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere

sealed class IslandCreationEvent {
    data class OnIslandNameChange(val newIslandName: String) : IslandCreationEvent()
    data class OnHemisphereChanged(val newHemisphere: Hemisphere) : IslandCreationEvent()
    data class OnNumberOfVillagersChanged(val numberOfVillagers: String) : IslandCreationEvent()
    object OnCreateClicked : IslandCreationEvent()
}
