package com.example.crossingschedule.feature.islands.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.R
import com.example.crossingschedule.feature.islands.presentation.components.IslandFilter
import com.example.crossingschedule.feature.islands.presentation.components.IslandList
import com.example.crossingschedule.feature.islands.presentation.model.IslandSelectionEvent
import com.example.crossingschedule.feature.islands.presentation.viewmodel.IslandSelectionViewModel

const val ISLAND_SELECTION_PAGE = "ISLAND_SELECTION_PAGE"

@Composable
fun IslandSelectionPage(
    islandSelectionViewModel: IslandSelectionViewModel
) {
    val islandSelectionViewState by islandSelectionViewModel.islandSelectionViewState.collectAsState()

    SideEffect {
        islandSelectionViewModel.onEvent(IslandSelectionEvent.GetAllIslands)
    }

    Scaffold {
        Column {
            Text(
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                text = stringResource(R.string.select_island),
                style = MaterialTheme.typography.h2,
            )
            IslandFilter(
                onIslandSelectionEvent = islandSelectionViewModel::onEvent,
                filterExpandedState = islandSelectionViewState.filterIslandExpanded,
            )
            IslandList(islands = islandSelectionViewState.islandData)
        }
    }
}