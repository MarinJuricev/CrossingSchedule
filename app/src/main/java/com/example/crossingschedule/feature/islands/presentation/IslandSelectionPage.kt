package com.example.crossingschedule.feature.islands.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.R
import com.example.crossingschedule.core.ui.components.CrossingCard
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
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.select_island),
                style = MaterialTheme.typography.h2,
            )
            LazyColumn {
                items(
                    count = islandSelectionViewState.islandData.size,
                    key = { index ->
                        islandSelectionViewState.islandData[index].id
                    }
                ) { index ->
                    CrossingCard(
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth()
                        ) {
                            Image(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .clip(MaterialTheme.shapes.medium)
                                    .size(36.dp)
                                    .align(Alignment.CenterVertically)
                                    .background(MaterialTheme.colors.primary),
                                painter = painterResource(id = R.drawable.person),
                                contentDescription = null,
                            )
                            Column {
                                Text(text = islandSelectionViewState.islandData[index].name)
                                Text(text = islandSelectionViewState.islandData[index].hemisphere.name)
                            }
                            Image(
//                                modifier = Modifier.align(Alignment.E),
                                painter = painterResource(id = R.drawable.arrow_forward),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }

}