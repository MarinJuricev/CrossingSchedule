package com.example.crossingschedule.feature.islandCreation.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.core.ui.components.CrossingCard
import com.example.crossingschedule.feature.islandCreation.presentation.components.IslandCreationContent
import com.example.crossingschedule.feature.islandCreation.presentation.viewmodel.IslandCreationViewModel

const val ISLAND_CREATION_PAGE = "ISLAND_CREATION_PAGE"

@Composable
fun IslandCreationPage(
    islandCreationViewModel: IslandCreationViewModel
) {

    val viewState by islandCreationViewModel.islandCreationViewState.collectAsState()

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