package com.example.crossingschedule.feature.islandSelection.presentation.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.R
import com.example.crossingschedule.core.ui.components.CrossingCard
import com.example.crossingschedule.core.ui.components.CrossingErrorCard
import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere
import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere.NORTH
import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere.SOUTH
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandInfo
import com.example.crossingschedule.feature.islandSelection.presentation.model.IslandSelectionEvent
import com.example.crossingschedule.feature.islandSelection.presentation.model.IslandSelectionEvent.IslandFilterGroupClicked
import com.example.crossingschedule.feature.islandSelection.presentation.model.IslandSelectionEvent.IslandFilterNewHemisphereSort

@Composable
fun EmptyIslandCard(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        CrossingErrorCard(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 16.dp),
            errorMessage = stringResource(id = R.string.empty_islands_message)
        )
    }
}

@Composable
fun IslandList(
    islands: List<IslandInfo>,
    navigateToSchedule: (islandId: String) -> Unit
) {
    LazyColumn {
        items(
            count = islands.size,
            key = { index -> islands[index].id }
        ) { index ->
            CrossingCard(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
                    .clickable { navigateToSchedule(islands[index].id) }
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
    currentHemisphereSelected: Hemisphere?,
    onIslandSelectionEvent: (IslandSelectionEvent) -> Unit,
    filterExpandedState: Boolean
) {
    val filterTransition = updateTransition(
        label = "filterTransition",
        targetState = filterExpandedState,
    )

    val filterHeight by filterTransition.animateDp(
        label = "filterHeight",
        transitionSpec = {
            spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow,
            )
        }
    ) {
        when (it) {
            true -> 128.dp
            false -> 48.dp
        }
    }

    val filterWidth by filterTransition.animateDp(
        label = "filterWidth",
        transitionSpec = {
            spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow,
            )
        }
    ) {
        when (it) {
            true -> 324.dp
            false -> 128.dp
        }
    }

    val filterContainerColor by filterTransition.animateColor(
        label = "filterContainerColor",
        transitionSpec = {
            spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow,
            )
        }
    ) {
        when (it) {
            true -> MaterialTheme.colors.primary
            false -> MaterialTheme.colors.background
        }
    }

    val filterButtonTintColor by filterTransition.animateColor(
        label = "filterButtonTintColor",
        transitionSpec = {
            spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow,
            )
        }
    ) {
        when (it) {
            true -> Color.Black
            false -> MaterialTheme.colors.background
        }
    }

    val filterButtonBackgroundColor by filterTransition.animateColor(
        label = "filterButtonBackgroundColor",
        transitionSpec = {
            spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow,
            )
        }
    ) {
        when (it) {
            true -> MaterialTheme.colors.background
            false -> MaterialTheme.colors.primary
        }
    }

    val filterOptionsVisibility by filterTransition.animateFloat(
        label = "filterOptionsVisibility",
        transitionSpec = {
            spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessVeryLow,
            )
        }
    ) {
        when (it) {
            true -> 1f
            false -> 0f
        }
    }

    Box(
        modifier = Modifier
            .padding(16.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(filterContainerColor)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .height(filterHeight)
                .width(filterWidth),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = filterButtonBackgroundColor
                    ),
                    onClick = {
                        onIslandSelectionEvent(IslandFilterGroupClicked)
                    }
                ) {
                    Icon(
                        contentDescription = null,
                        imageVector = Icons.Default.Add,
                        tint = filterButtonTintColor
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(R.string.filter),
                        style = MaterialTheme.typography.h3.copy(
                            color = filterButtonTintColor
                        ),
                    )
                }
            }
            Row(
                modifier = Modifier
                    .alpha(filterOptionsVisibility)
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.hemisphere_filter),
                    style = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.background
                    ),
                )
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = currentHemisphereSelected == NORTH,
                        onClick = { onIslandSelectionEvent(IslandFilterNewHemisphereSort(NORTH)) },
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                    Text(
                        text = stringResource(R.string.north),
                        style = MaterialTheme.typography.body1.copy(
                            color = MaterialTheme.colors.background
                        ),
                    )
                }
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = currentHemisphereSelected == SOUTH,
                        onClick = { onIslandSelectionEvent(IslandFilterNewHemisphereSort(SOUTH)) },
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                    Text(
                        text = stringResource(R.string.south),
                        style = MaterialTheme.typography.body1.copy(
                            color = MaterialTheme.colors.background
                        ),
                    )
                }
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = currentHemisphereSelected == null,
                        onClick = { onIslandSelectionEvent(IslandFilterNewHemisphereSort(null)) },
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                    Text(
                        text = stringResource(R.string.none),
                        style = MaterialTheme.typography.body1.copy(
                            color = MaterialTheme.colors.background
                        ),
                    )
                }
            }
        }
    }
}