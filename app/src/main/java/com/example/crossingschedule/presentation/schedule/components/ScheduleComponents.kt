package com.example.crossingschedule.presentation.schedule.components

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.DpPropKey
import androidx.compose.animation.core.FloatPropKey
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.core.tween
import androidx.compose.animation.transition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.gesture.longPressGestureFilter
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.R
import com.example.crossingschedule.domain.model.CrossingTodo
import com.example.crossingschedule.domain.model.VillagerInteraction
import com.example.crossingschedule.presentation.core.components.CrossingCard
import com.example.crossingschedule.presentation.core.ui.crossingTypography
import com.example.crossingschedule.presentation.schedule.model.TodoContainerState
import com.example.crossingschedule.presentation.schedule.model.UiShop
import com.example.crossingschedule.presentation.schedule.model.UiTurnipPrices

@Composable
fun BackgroundImage(
    @DrawableRes resourceId: Int,
    modifier: Modifier = Modifier,
) {
    Image(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentScale = ContentScale.FillBounds,
        bitmap = imageFromResource(
            AmbientContext.current.resources,
            resourceId
        )
    )
}

@Composable
fun DailyCheckListCard(modifier: Modifier) {
    Card(
        modifier = modifier,
        elevation = 4.dp,
        shape = RoundedCornerShape(topRight = 16.dp, bottomRight = 16.dp),
    ) {
        Text(
            modifier = Modifier
                .padding(start = 24.dp, top = 8.dp, bottom = 8.dp, end = 16.dp),
            text = stringResource(R.string.daily_checklist),
            style = crossingTypography.h2,
        )
    }
}

@Composable
fun CurrentDateCard(
    modifier: Modifier,
    dateToDisplay: String
) {
    CrossingCard(modifier = modifier) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            text = stringResource(
                id = R.string.current_date,
                dateToDisplay
            ),
            textAlign = TextAlign.Center,
            style = crossingTypography.h6.copy(fontWeight = FontWeight.Bold),
        )
    }
}

@Composable
fun RawIngredientRow(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        //TODO: Get the ingredients from BE and just map through the list
        Image(
            modifier = Modifier
                .size(42.dp)
                .padding(top = 4.dp),
            bitmap = imageFromResource(
                AmbientContext.current.resources,
                R.drawable.tree_branch
            )
        )
        Image(
            modifier = Modifier
                .size(42.dp)
                .padding(bottom = 4.dp),
            bitmap = imageFromResource(
                AmbientContext.current.resources,
                R.drawable.stone
            )
        )
        Image(
            modifier = Modifier
                .size(42.dp)
                .padding(top = 4.dp),
            bitmap = imageFromResource(
                AmbientContext.current.resources,
                R.drawable.clay
            )
        )
        Image(
            modifier = Modifier
                .size(42.dp)
                .padding(bottom = 4.dp),
            bitmap = imageFromResource(
                AmbientContext.current.resources,
                R.drawable.hard_wood
            )
        )
        Image(
            modifier = Modifier
                .size(42.dp)
                .padding(top = 4.dp),
            bitmap = imageFromResource(
                AmbientContext.current.resources,
                R.drawable.iron_nugget
            )
        )
    }
}

val crossingTodoHeight = DpPropKey(label = "crossingTodoHeight")
val addTodoOpacity = FloatPropKey(label = "addTodoOpacity")

val transitionDefinition = transitionDefinition<TodoContainerState> {
    state(TodoContainerState.IDLE) {
        this[crossingTodoHeight] = 36.dp
        this[addTodoOpacity] = 0f
    }

    state(TodoContainerState.PRESSED) {
        this[crossingTodoHeight] = 120.dp
        this[addTodoOpacity] = 1f
    }

    state(TodoContainerState.DO_NOT_ANIMATE) {
        this[crossingTodoHeight] = 36.dp
        this[addTodoOpacity] = 0f
    }

    transition(fromState = TodoContainerState.IDLE, toState = TodoContainerState.PRESSED) {
        crossingTodoHeight using tween(durationMillis = 750)
        addTodoOpacity using tween(
            durationMillis = 1000,
            delayMillis = 500,
            easing = LinearOutSlowInEasing
        )
    }

    transition(fromState = TodoContainerState.PRESSED, toState = TodoContainerState.IDLE) {
        crossingTodoHeight using tween(durationMillis = 750)
        addTodoOpacity using tween(
            durationMillis = 1000,
            delayMillis = 500,
            easing = LinearOutSlowInEasing
        )
    }
}

@Composable
fun CrossingTodoList(
    modifier: Modifier = Modifier,
    todos: List<CrossingTodo>,
    onDoneClick: (List<CrossingTodo>, CrossingTodo) -> Unit,
    onNewTodoCreated: (List<CrossingTodo>, String) -> Unit
) {
    CrossingCard(
        modifier = modifier
    ) {
        Column {
            AnimatedAddTodoContainer(
                todos = todos,
                onNewTodoCreated = onNewTodoCreated
            )
            Divider()
            LazyColumn(
                modifier = Modifier
                    .padding(bottom = 8.dp, top = 8.dp),
                content = {
                    items(todos) { todo ->
                        Row(
                            modifier = Modifier
                                .padding(4.dp)
                                .longPressGestureFilter {
                                    Log.d("TEST", "TEST")
                                },
                            verticalAlignment = Alignment.CenterVertically,

                            ) {
                            Checkbox(
                                checked = todo.isDone,
                                onCheckedChange = { onDoneClick(todos, todo) },
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = todo.message,
                                style = crossingTypography.h4,
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun AnimatedAddTodoContainer(
    todos: List<CrossingTodo>,
    onNewTodoCreated: (List<CrossingTodo>, String) -> Unit
) {
    val containerState = remember { mutableStateOf(TodoContainerState.DO_NOT_ANIMATE) }
    val addTodoText = remember { mutableStateOf("") }

    val toState = when (containerState.value) {
        TodoContainerState.IDLE -> TodoContainerState.PRESSED
        TodoContainerState.DO_NOT_ANIMATE -> TodoContainerState.DO_NOT_ANIMATE
        else -> TodoContainerState.IDLE
    }

    val addTodoContainerState = transition(
        definition = transitionDefinition,
        initState = containerState.value,
        toState = toState,
    )

    Column(
        modifier = Modifier
            .height(addTodoContainerState[crossingTodoHeight]),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.add_todo),
            )

            IconButton(onClick = {
                containerState.value =
                    when (containerState.value) {
                        TodoContainerState.IDLE -> TodoContainerState.PRESSED
                        TodoContainerState.DO_NOT_ANIMATE, TodoContainerState.PRESSED -> TodoContainerState.IDLE
                    }
            }) {
                Icon(imageVector = Icons.Default.Add)
            }
        }
        if (containerState.value == TodoContainerState.IDLE) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                    .alpha(addTodoContainerState[addTodoOpacity]),
                value = addTodoText.value,
                onValueChange = { addTodoText.value = it },
                singleLine = true,
                label = { Text(stringResource(R.string.create_todo)) },
                onImeActionPerformed = { imeAction, _ ->
                    if (imeAction == ImeAction.Done) {
                        onNewTodoCreated(todos, addTodoText.value)
                        containerState.value = TodoContainerState.PRESSED
                    }
                }
            )
        }
    }
}

@Composable
fun CrossingShops(
    shops: List<UiShop>,
    modifier: Modifier = Modifier
) {
    CrossingCard(modifier = modifier) {
        Column {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                text = stringResource(R.string.check_each_shop),
                textAlign = TextAlign.Center,
                style = crossingTypography.h6.copy(fontWeight = FontWeight.Bold),
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                content = {
                    items(shops) { shop ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(42.dp)
                                    .padding(vertical = 8.dp),
                                bitmap = imageFromResource(
                                    AmbientContext.current.resources,
                                    shop.resourceImageId
                                )
                            )
                            Checkbox(
                                checked = shop.isVisited,
                                onCheckedChange = { })
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun TurnipPriceList(modifier: Modifier = Modifier, turnipPrices: UiTurnipPrices) {
    CrossingCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.turnip_prices),
                    style = crossingTypography.h6.copy(fontWeight = FontWeight.Bold),
                )
                Image(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(36.dp),
                    bitmap = imageFromResource(
                        AmbientContext.current.resources,
                        R.drawable.daisy_mae
                    )
                )
            }
            OutlinedTextField(
                modifier = Modifier.padding(horizontal = 8.dp),
                value = turnipPrices.amPrice,
                singleLine = true,
                maxLines = 1,
                label = {
                    Text(
                        text = stringResource(id = R.string.turnip_am_price),
                        fontSize = TextUnit.Sp(10)
                    )
                },
                onValueChange = { },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            OutlinedTextField(
                modifier = Modifier.padding(horizontal = 8.dp),
                value = turnipPrices.pmPrice,
                singleLine = true,
                maxLines = 1,
                label = {
                    Text(
                        text = stringResource(id = R.string.turnip_pm_price),
                        fontSize = TextUnit.Sp(10)
                    )
                },
                onValueChange = { },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
        }
    }
}

@Composable
fun VillagerInteractionsList(
    villagerInteractions: List<VillagerInteraction>,
    modifier: Modifier = Modifier
) {
    CrossingCard(modifier = modifier) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = stringResource(R.string.villagers),
                style = crossingTypography.h6.copy(fontWeight = FontWeight.Bold),
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                content = {
                    itemsIndexed(villagerInteractions) { index, villagerInteraction ->
                        Row {
                            Text(text = (index + 1).toString())
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                text = villagerInteraction.villagerName,
                            )
                        }
                    }
                },
            )
        }
    }
}

@Composable
fun Notes(modifier: Modifier = Modifier, notes: String = "") {
    CrossingCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .preferredHeight(150.dp)
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(R.string.notes),
                style = crossingTypography.h6.copy(fontWeight = FontWeight.Bold),
            )
            Text(text = notes)
        }
    }
}