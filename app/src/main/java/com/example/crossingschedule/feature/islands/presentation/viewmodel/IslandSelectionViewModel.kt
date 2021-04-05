package com.example.crossingschedule.feature.islands.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.crossingschedule.core.BaseViewModel
import com.example.crossingschedule.core.model.Either.Left
import com.example.crossingschedule.core.model.Either.Right
import com.example.crossingschedule.feature.islands.domain.usecase.GetIslands
import com.example.crossingschedule.feature.islands.presentation.model.IslandSelectionEvent
import com.example.crossingschedule.feature.islands.presentation.model.IslandSelectionEvent.GetAllIslands
import com.example.crossingschedule.feature.islands.presentation.model.IslandSelectionViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IslandSelectionViewModel @Inject constructor(
    private val getIslands: GetIslands
) : BaseViewModel<IslandSelectionEvent>() {

    private val _islandSelectionViewState = MutableStateFlow(IslandSelectionViewState())
    val islandSelectionViewState: StateFlow<IslandSelectionViewState> = _islandSelectionViewState

    override fun onEvent(event: IslandSelectionEvent) {
        when (event) {
            GetAllIslands -> getIslandInformation()
        }
    }

    private fun getIslandInformation() = viewModelScope.launch {
        when (val result = getIslands()) {
            is Right -> _islandSelectionViewState.value =
                _islandSelectionViewState.value.copy(
                    islandData = result.value
                )
            is Left -> _islandSelectionViewState.value =
                _islandSelectionViewState.value.copy(
                    errorMessage = result.error.errorMessage
                )
        }
    }
}