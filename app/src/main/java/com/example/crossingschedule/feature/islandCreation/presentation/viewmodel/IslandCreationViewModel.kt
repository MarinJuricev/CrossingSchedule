package com.example.crossingschedule.feature.islandCreation.presentation.viewmodel

import com.example.crossingschedule.core.BaseViewModel
import com.example.crossingschedule.feature.islandCreation.domain.usecase.CreateIsland
import com.example.crossingschedule.feature.islandCreation.presentation.model.IslandCreationEvent
import com.example.crossingschedule.feature.islandCreation.presentation.model.IslandCreationEvent.*
import com.example.crossingschedule.feature.islandCreation.presentation.model.IslandCreationViewState
import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class IslandCreationViewModel @Inject constructor(
    private val createIsland: CreateIsland
) : BaseViewModel<IslandCreationEvent>() {

    private val _islandCreationViewState = MutableStateFlow(IslandCreationViewState())
    val islandCreationViewState: StateFlow<IslandCreationViewState> = _islandCreationViewState

    override fun onEvent(event: IslandCreationEvent) {
        when (event) {
            is OnIslandNameChange -> onIslandNameChange(event.newIslandName)
            is OnHemisphereChanged -> onHemisphereChanged(event.newHemisphere)
            is OnNumberOfVillagersChanged -> onNumberOfVillagersChanged(event.numberOfVillagers)
            OnCreateClicked -> onCreateClicked()
        }
    }

    private fun onIslandNameChange(newIslandName: String) {
        _islandCreationViewState.value = _islandCreationViewState.value.copy(
            name = newIslandName
        )
    }

    private fun onHemisphereChanged(newHemisphere: Hemisphere) {
        _islandCreationViewState.value = _islandCreationViewState.value.copy(
            hemisphere = newHemisphere
        )
    }

    private fun onNumberOfVillagersChanged(numberOfVillagers: String) {
        _islandCreationViewState.value = _islandCreationViewState.value.copy(
            numberOfVillagers = numberOfVillagers
        )
    }

    private fun onCreateClicked() {

    }
}