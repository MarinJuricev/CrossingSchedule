package com.example.crossingschedule.feature.islands.presentation.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
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
            key = { index -> islands[index].id }
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
                        onIslandSelectionEvent(IslandFilterStateChanged)
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
                        selected = true,
                        onClick = {})
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
                        selected = false,
                        onClick = {})
                    Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                    Text(
                        text = stringResource(R.string.south),
                        style = MaterialTheme.typography.body1.copy(
                            color = MaterialTheme.colors.background
                        ),
                    )
                }
            }
        }
    }
}