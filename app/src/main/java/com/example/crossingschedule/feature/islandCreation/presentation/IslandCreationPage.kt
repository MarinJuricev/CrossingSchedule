package com.example.crossingschedule.feature.islandCreation.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.core.ui.components.CrossingCard
import com.example.crossingschedule.feature.islandCreation.presentation.components.IslandCreationContent
import com.example.crossingschedule.feature.islandCreation.presentation.model.IslandCreationError
import com.example.crossingschedule.feature.islandCreation.presentation.viewmodel.IslandCreationViewModel

const val ISLAND_CREATION_PAGE = "ISLAND_CREATION_PAGE"

@Composable
fun IslandCreationPage(
    islandCreationViewModel: IslandCreationViewModel,
    popBackStack: () -> Unit,
) {

    val viewState by islandCreationViewModel.islandCreationViewState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    viewState.islandCreationSuccess?.let {
        LaunchedEffect(key1 = viewState.islandCreationSuccess) {
            popBackStack()
        }
    }

    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = snackBarHostState)
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.primary)
                .fillMaxSize()
        ) {
            CrossingCard(
                modifier = Modifier
                    .background(MaterialTheme.colors.primary)
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(36.dp)
            ) {
                IslandCreationContent(
                    islandCreationViewModel::onEvent,
                    viewState,
                )
            }
        }
    }
    if (viewState.islandCreationError is IslandCreationError.GeneralError) {
        LaunchedEffect(
            key1 = viewState.islandCreationError,
            block = {
                snackBarHostState.showSnackbar(
                    message = viewState.islandCreationError!!.error,
                )
            },
        )
    }
}