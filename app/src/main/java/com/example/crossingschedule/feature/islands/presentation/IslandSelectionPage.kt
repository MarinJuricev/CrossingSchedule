package com.example.crossingschedule.feature.islands.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
    islandSelectionViewModel: IslandSelectionViewModel,
    navigateToSchedule: (islandId: String) -> Unit,
    navigateToIslandCreation: () -> Unit,
) {
    val islandSelectionViewState by islandSelectionViewModel.islandSelectionViewState.collectAsState()

    SideEffect {
        islandSelectionViewModel.onEvent(IslandSelectionEvent.GetAllIslands)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToIslandCreation() },
                contentColor = MaterialTheme.colors.background,
            ) {
                Icon(
                    Icons.Default.Add,
                    null,
                )
            }
        }
    ) {
        Column {
            Text(
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                text = stringResource(R.string.select_island),
                style = MaterialTheme.typography.h2,
            )
            IslandFilter(
                currentHemisphereSelected = islandSelectionViewState.hemisphereToFilter,
                onIslandSelectionEvent = islandSelectionViewModel::onEvent,
                filterExpandedState = islandSelectionViewState.filterIslandExpanded,
            )
            IslandList(
                islands = islandSelectionViewState.islandData,
                navigateToSchedule = navigateToSchedule
            )
        }
    }
}