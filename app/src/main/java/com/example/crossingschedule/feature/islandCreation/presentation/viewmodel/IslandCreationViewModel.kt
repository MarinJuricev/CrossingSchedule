package com.example.crossingschedule.feature.islandCreation.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.crossingschedule.core.BaseViewModel
import com.example.crossingschedule.core.model.Either.Left
import com.example.crossingschedule.core.model.Either.Right
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.islandCreation.domain.model.IslandCreationFailure
import com.example.crossingschedule.feature.islandCreation.domain.usecase.CreateIsland
import com.example.crossingschedule.feature.islandCreation.presentation.model.IslandCreationError
import com.example.crossingschedule.feature.islandCreation.presentation.model.IslandCreationEvent
import com.example.crossingschedule.feature.islandCreation.presentation.model.IslandCreationEvent.*
import com.example.crossingschedule.feature.islandCreation.presentation.model.IslandCreationViewState
import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IslandCreationViewModel @Inject constructor(
    private val createIsland: CreateIsland,
    private val islandCreationFailureToErrorMapper: Mapper<IslandCreationError, IslandCreationFailure>,
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
        triggerIsLoading()
        val viewState = islandCreationViewState.value

        viewModelScope.launch {
            when (val result = createIsland(
                islandName = viewState.name,
                hemisphere = viewState.hemisphere,
                numberOfVillagers = viewState.numberOfVillagers,
            )) {
                is Left -> _islandCreationViewState.value =
                    _islandCreationViewState.value.copy(
                        islandCreationError = islandCreationFailureToErrorMapper.map(result.error),
                        isLoading = false,
                    )
                is Right -> _islandCreationViewState.value =
                    _islandCreationViewState.value.copy(
                        islandCreationSuccess = Unit, //TODO Implement some kind of navigator and inject it into viewmodels...
                        isLoading = false,
                    )
            }
        }
    }

    private fun triggerIsLoading() {
        _islandCreationViewState.value =
            _islandCreationViewState.value.copy(
                isLoading = true
            )
    }
}