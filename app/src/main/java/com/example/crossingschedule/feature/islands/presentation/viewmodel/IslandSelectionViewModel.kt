package com.example.crossingschedule.feature.islands.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.crossingschedule.core.BaseViewModel
import com.example.crossingschedule.feature.islands.domain.usecase.GetIslands
import com.example.crossingschedule.feature.islands.presentation.model.IslandSelectionEvent
import com.example.crossingschedule.feature.islands.presentation.model.IslandSelectionEvent.GetAllIslands
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IslandSelectionViewModel @Inject constructor(
    private val getIslands: GetIslands
) : BaseViewModel<IslandSelectionEvent>() {
    override fun onEvent(event: IslandSelectionEvent) {
        when (event) {
            GetAllIslands -> getIslandInformation()
        }
    }

    private fun getIslandInformation() = viewModelScope.launch {

    }
}