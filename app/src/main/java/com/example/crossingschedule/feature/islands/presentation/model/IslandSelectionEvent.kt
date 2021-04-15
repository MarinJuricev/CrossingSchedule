package com.example.crossingschedule.feature.islands.presentation.model

import com.example.crossingschedule.feature.islands.domain.model.Hemisphere

sealed class IslandSelectionEvent {
    object GetAllIslands : IslandSelectionEvent()
    object IslandFilterGroupClicked : IslandSelectionEvent()
    data class IslandFilterNewHemisphereSort(val newHemisphereSort: Hemisphere) :
        IslandSelectionEvent()
}
