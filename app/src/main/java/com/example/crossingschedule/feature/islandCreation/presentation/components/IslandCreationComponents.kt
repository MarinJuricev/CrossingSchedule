package com.example.crossingschedule.feature.islandCreation.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.R
import com.example.crossingschedule.feature.islandCreation.presentation.model.IslandCreationEvent
import com.example.crossingschedule.feature.islandCreation.presentation.model.IslandCreationEvent.*
import com.example.crossingschedule.feature.islandCreation.presentation.model.IslandCreationViewState
import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere

@Composable
fun IslandCreationContent(
    eventHandler: (IslandCreationEvent) -> Unit,
    viewState: IslandCreationViewState,
) {
    Column(
        modifier = Modifier.fillMaxWidth(0.8f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .size(64.dp),
            painter = painterResource(id = R.drawable.icon_island),
            contentDescription = null,
        )
        Text(
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
            text = stringResource(R.string.create_an_island),
            style = MaterialTheme.typography.h2,
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = viewState.name,
            onValueChange = { eventHandler(OnIslandNameChange(it)) },
            singleLine = true,
            label = { Text(stringResource(R.string.island_name)) },
        )
        HemisphereSelection(
            eventHandler,
            viewState,
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = viewState.numberOfVillagers,
            onValueChange = { eventHandler(OnNumberOfVillagersChanged(it)) },
            singleLine = true,
            label = { Text(stringResource(R.string.number_of_villagers)) },
        )
        OutlinedButton(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.End),
            onClick = { eventHandler(OnCreateClicked) }) {
            Text(
                text = stringResource(R.string.create),
                style = TextStyle(color = MaterialTheme.colors.onSurface)
            )
        }
    }
}

@Composable
fun HemisphereSelection(
    eventHandler: (IslandCreationEvent) -> Unit,
    viewState: IslandCreationViewState,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.hemisphere_selection),
            style = MaterialTheme.typography.h4,
        )
        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = viewState.hemisphere == Hemisphere.NORTH,
                    onClick = { eventHandler(OnHemisphereChanged(Hemisphere.NORTH)) },
                )
                Spacer(modifier = Modifier.padding(start = 4.dp))
                Text(
                    text = stringResource(R.string.north),
                    style = MaterialTheme.typography.body1,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = viewState.hemisphere == Hemisphere.SOUTH,
                    onClick = { eventHandler(OnHemisphereChanged(Hemisphere.SOUTH)) },
                )
                Spacer(modifier = Modifier.padding(start = 4.dp))
                Text(
                    text = stringResource(R.string.south),
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    }
}