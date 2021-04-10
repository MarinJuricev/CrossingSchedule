package com.example.crossingschedule.feature.islands.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.R
import com.example.crossingschedule.core.ui.components.CrossingCard
import com.example.crossingschedule.feature.islands.domain.model.IslandInfo
import com.example.crossingschedule.feature.islands.presentation.model.IslandSelectionEvent
import com.example.crossingschedule.feature.islands.presentation.model.IslandSelectionEvent.IslandFilterStateChanged

@Composable
fun IslandList(
    modifier: Modifier = Modifier,
    islands: List<IslandInfo>
) {
    LazyColumn {
        items(
            count = islands.size,
            key = { index ->
                islands[index].id
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
                        Text(text = islands[index].name)
                        Text(text = islands[index].hemisphere.name)
                    }
                    Image(
                        painter = painterResource(id = R.drawable.arrow_forward),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
fun IslandFilter(
    onIslandSelectionEvent: (IslandSelectionEvent) -> Unit,
    filterExpandedState: Boolean
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { onIslandSelectionEvent(IslandFilterStateChanged) }) {
            Row {
                Icon(
                    contentDescription = null,
                    imageVector = Icons.Default.Add
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = stringResource(R.string.filter),
                    style = MaterialTheme.typography.h3,
                )
            }
        }
        if (filterExpandedState) {
            Row {
                Icon(
                    contentDescription = null,
                    imageVector = Icons.Default.Add
                )
                Text(
                    text = stringResource(R.string.filter),
                    style = MaterialTheme.typography.h3,
                )
            }
        }
    }
}