package com.example.crossingschedule.feature.islandSelection.presentation.model

import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere

sealed class IslandSelectionEvent {
    object GetAllIslands : IslandSelectionEvent()
    object IslandFilterGroupClicked : IslandSelectionEvent()
    data class IslandFilterNewHemisphereSort(val newHemisphereSort: Hemisphere?) :
        IslandSelectionEvent()
}
