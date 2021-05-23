package com.example.crossingschedule.feature.islandCreation.presentation.components

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.R
import com.example.crossingschedule.feature.islandCreation.presentation.model.AnimatedIslandCreationValidatorState.*
import com.example.crossingschedule.feature.islandCreation.presentation.model.IslandCreationError.*
import com.example.crossingschedule.feature.islandCreation.presentation.model.IslandCreationEvent
import com.example.crossingschedule.feature.islandCreation.presentation.model.IslandCreationEvent.*
import com.example.crossingschedule.feature.islandCreation.presentation.model.IslandCreationViewState
import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere

@Composable
fun IslandCreationContent(
    eventHandler: (IslandCreationEvent) -> Unit,
    viewState: IslandCreationViewState,
) {
    var validatorAnimatedStates by remember { mutableStateOf(NO_ERROR) }

    validatorAnimatedStates = when (viewState.islandCreationError) {
        is NameError -> ISLAND_ERROR
        is HemisphereError -> HEMISPHERE_ERROR
        is NumberOfVillagersError -> NUMBER_OF_VILLAGERS_ERROR
        else -> NO_ERROR
    }

    val validatorTransition = updateTransition(
        label = "validatorTransition",
        targetState = validatorAnimatedStates
    )

    val islandNameAlpha by validatorTransition.animateFloat(
        label = "islandName",
        transitionSpec = {
            tween(durationMillis = 1000)
        },
    ) {
        when (it) {
            ISLAND_ERROR -> 1f
            else -> 0f
        }
    }

    val islandNameHeight = validatorTransition.animateDp(
        label = "islandNameHeight",
        transitionSpec = {
            tween(durationMillis = 1000)
        },
    ) {
        when (it) {
            ISLAND_ERROR -> 12.dp
            else -> 0.dp
        }
    }

    val hemisphereAlpha by validatorTransition.animateFloat(
        label = "hemisphereAlpha",
        transitionSpec = {
            tween(durationMillis = 1000)
        },
    ) {
        when (it) {
            HEMISPHERE_ERROR -> 1f
            else -> 0f
        }
    }

    val hemisphereHeight = validatorTransition.animateDp(
        label = "hemisphereHeight",
        transitionSpec = {
            tween(durationMillis = 1000)
        },
    ) {
        when (it) {
            HEMISPHERE_ERROR -> 12.dp
            else -> 0.dp
        }
    }

    val numberOfVillagersAlpha by validatorTransition.animateFloat(
        label = "numberOfVillagersAlpha",
        transitionSpec = {
            tween(durationMillis = 1000)
        },
    ) {
        when (it) {
            NUMBER_OF_VILLAGERS_ERROR -> 1f
            else -> 0f
        }
    }

    val numberOfVillagersHeight = validatorTransition.animateDp(
        label = "numberOfVillagersHeight",
        transitionSpec = {
            tween(durationMillis = 1000)
        },
    ) {
        when (it) {
            NUMBER_OF_VILLAGERS_ERROR -> 12.dp
            else -> 0.dp
        }
    }

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
        Text(
            modifier = Modifier
                .alpha(islandNameAlpha)
                .fillMaxWidth()
                .padding(islandNameHeight.value),
            text = (viewState.islandCreationError as? NameError)?.nameError
                ?: "",//TODO Cleaner API for this... this is mega messy
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
        )
        HemisphereSelection(
            eventHandler,
            viewState,
        )
        Text(
            modifier = Modifier
                .alpha(hemisphereAlpha)
                .fillMaxWidth()
                .padding(hemisphereHeight.value),
            text = (viewState.islandCreationError as? HemisphereError)?.hemisphereError
                ?: "",//TODO Cleaner API for this... this is mega messy
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = viewState.numberOfVillagers,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            onValueChange = { eventHandler(OnNumberOfVillagersChanged(it)) },
            singleLine = true,
            label = { Text(stringResource(R.string.number_of_villagers)) },
        )
        Text(
            modifier = Modifier
                .alpha(numberOfVillagersAlpha)
                .fillMaxWidth()
                .padding(numberOfVillagersHeight.value),
            text = (viewState.islandCreationError as? NumberOfVillagersError)?.numberOfVillagersError
                ?: "",//TODO Cleaner API for this... this is mega messy
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
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