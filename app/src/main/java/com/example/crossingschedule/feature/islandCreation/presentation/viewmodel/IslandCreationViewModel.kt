package com.example.crossingschedule.feature.islandCreation.presentation.viewmodel

import com.example.crossingschedule.core.BaseViewModel
import com.example.crossingschedule.feature.islandCreation.presentation.model.IslandCreationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IslandCreationViewModel @Inject constructor(

) : BaseViewModel<IslandCreationEvent>() {

    override fun onEvent(event: IslandCreationEvent) {
        when (event) {

        }
    }
}