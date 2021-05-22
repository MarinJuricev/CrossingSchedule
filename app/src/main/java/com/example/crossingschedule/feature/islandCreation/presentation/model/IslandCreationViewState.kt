package com.example.crossingschedule.feature.islandCreation.presentation.model

import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere

data class IslandCreationViewState(
    val isLoading: Boolean = false,
    val name: String = "",
    val hemisphere: Hemisphere? = null,
    val numberOfVillagers: String = "",
    val islandCreationError: IslandCreationError? = null,
)

sealed class IslandCreationError(val error: String) {
    data class NameError(val nameError: String) : IslandCreationError(nameError)
    data class HemisphereError(val hemisphereError: String) : IslandCreationError(hemisphereError)
    data class NumberOfVillagersError(val numberOfVillagersError: String) :
        IslandCreationError(numberOfVillagersError)
    data class GeneralError(val errorMessage: String) : IslandCreationError(errorMessage)
}
