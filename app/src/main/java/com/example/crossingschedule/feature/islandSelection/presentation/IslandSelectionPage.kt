package com.example.crossingschedule.feature.islandSelection.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.R
import com.example.crossingschedule.core.ui.components.PullToRefreshLoadingContent
import com.example.crossingschedule.feature.islandSelection.presentation.components.EmptyIslandCard
import com.example.crossingschedule.feature.islandSelection.presentation.components.IslandFilter
import com.example.crossingschedule.feature.islandSelection.presentation.components.IslandList
import com.example.crossingschedule.feature.islandSelection.presentation.model.IslandSelectionEvent.GetAllIslands
import com.example.crossingschedule.feature.islandSelection.presentation.viewmodel.IslandSelectionViewModel

const val ISLAND_SELECTION_PAGE = "ISLAND_SELECTION_PAGE"

@Composable
fun IslandSelectionPage(
    islandSelectionViewModel: IslandSelectionViewModel,
    navigateToSchedule: (islandId: String) -> Unit,
    navigateToIslandCreation: () -> Unit,
) {
    val islandSelectionViewState by islandSelectionViewModel.islandSelectionViewState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = Unit) {
        islandSelectionViewModel.onEvent(GetAllIslands)
    }

    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = snackBarHostState),
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
            PullToRefreshLoadingContent(
                isEmpty = islandSelectionViewState.islandData.isEmpty(),
                emptyContent = {
                    EmptyIslandCard(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    )
                },
                isLoading = islandSelectionViewState.isLoading,
                onRefresh = { islandSelectionViewModel.onEvent(GetAllIslands) },
                content = {
                    IslandList(
                        islands = islandSelectionViewState.islandData,
                        navigateToSchedule = navigateToSchedule
                    )
                }
            )
        }
        if (islandSelectionViewState.errorMessage.isNotEmpty()) {
            LaunchedEffect(
                key1 = islandSelectionViewState.errorMessage,
                block = {
                    snackBarHostState.showSnackbar(islandSelectionViewState.errorMessage)
                }
            )
        }
    }
}