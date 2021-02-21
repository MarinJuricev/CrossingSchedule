package com.example.crossingschedule.feature.schedule.presentation.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.R
import com.example.crossingschedule.feature.schedule.domain.model.CrossingTodo
import com.example.crossingschedule.feature.schedule.domain.model.TurnipPriceType
import com.example.crossingschedule.feature.schedule.domain.model.VillagerInteraction
import com.example.crossingschedule.feature.schedule.presentation.model.AnimatedContainerState
import com.example.crossingschedule.feature.schedule.presentation.model.DateOptions
import com.example.crossingschedule.feature.schedule.presentation.model.UiShop
import com.example.crossingschedule.feature.schedule.presentation.model.UiTurnipPrices
import com.example.crossingschedule.core.ui.components.AnimatedContainer
import com.example.crossingschedule.core.ui.components.CrossingCard

@Composable
fun BackgroundImage(
    @DrawableRes resourceId: Int,
    modifier: Modifier = Modifier,
) {
    Image(
        contentDescription = null,
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
fun DailyCheckListCard(
    modifier: Modifier
) {
    Card(
        modifier = modifier,
        elevation = 4.dp,
        shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp),
    ) {
        Text(
            modifier = Modifier
                .padding(start = 24.dp, top = 8.dp, bottom = 8.dp, end = 16.dp),
            text = stringResource(R.string.daily_checklist),
            style = MaterialTheme.typography.h2,
        )
    }
}

@Composable
fun CurrentDateCard(
    modifier: Modifier,
    dateOptions: DateOptions,
    onDateChanged: (Int, Int, Int) -> Unit
) {
    val context = AmbientContext.current

    CrossingCard(
        modifier = modifier
            .clickable(
                onClick = {
                    DatePickerDialog(
                        context,
                        { _: DatePicker, pickedYear: Int, pickedMonth: Int, pickedDay: Int ->
                            onDateChanged(pickedYear, pickedMonth, pickedDay)
                        }, dateOptions.year, dateOptions.month, dateOptions.day
                    ).show()
                },
            )
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp),
            text = stringResource(
                id = R.string.current_date,
                dateOptions.formattedDate
            ),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
        )
    }
}

@Composable
fun RawIngredientRow(
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        //TODO: Get the ingredients from BE and just map through the list
        Image(
            contentDescription = null,
            modifier = Modifier
                .size(42.dp)
                .padding(top = 4.dp),
            bitmap = imageFromResource(
                AmbientContext.current.resources,
                R.drawable.tree_branch
            )
        )
        Image(
            contentDescription = null,
            modifier = Modifier
                .size(42.dp)
                .padding(bottom = 4.dp),
            bitmap = imageFromResource(
                AmbientContext.current.resources,
                R.drawable.stone
            )
        )
        Image(
            contentDescription = null,
            modifier = Modifier
                .size(42.dp)
                .padding(top = 4.dp),
            bitmap = imageFromResource(
                AmbientContext.current.resources,
                R.drawable.clay
            )
        )
        Image(
            contentDescription = null,
            modifier = Modifier
                .size(42.dp)
                .padding(bottom = 4.dp),
            bitmap = imageFromResource(
                AmbientContext.current.resources,
                R.drawable.hard_wood
            )
        )
        Image(
            contentDescription = null,
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

@Composable
fun CrossingTodoList(
    modifier: Modifier = Modifier,
    todos: List<CrossingTodo>,
    onDoneClick: (CrossingTodo) -> Unit,
    onNewTodoCreated: (String) -> Unit,
    onTodoDeleted: (CrossingTodo) -> Unit
) {
    CrossingCard(
        modifier = modifier
    ) {
        Column {
            AnimatedAddTodoContainer(
                onNewTodoCreated = onNewTodoCreated
            )
            Divider()
            LazyColumn(
                modifier = Modifier
                    .padding(bottom = 8.dp, top = 8.dp),
                content = {
                    items(todos.size) { index ->
                        Row(
                            modifier = Modifier
                                .padding(4.dp)
                                .longPressGestureFilter {
                                    onTodoDeleted(todos[index])
                                },
                            verticalAlignment = Alignment.CenterVertically,

                            ) {
                            Checkbox(
                                checked = todos[index].isDone,
                                onCheckedChange = { onDoneClick(todos[index]) },
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = todos[index].message,
                                style = MaterialTheme.typography.h4,
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
    onNewTodoCreated: (String) -> Unit
) {
    val addTodoText = remember { mutableStateOf("") }
    val animationState = remember { mutableStateOf(AnimatedContainerState.IDLE) }

    AnimatedContainer(
        animationState = animationState,
        nonExpandedContent = {
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
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold)
                )

                IconButton(
                    onClick = {
                        animationState.value =
                            when (animationState.value) {
                                AnimatedContainerState.IDLE -> AnimatedContainerState.PRESSED
                                AnimatedContainerState.PRESSED -> AnimatedContainerState.IDLE
                            }
                    },
                ) {
                    Icon(
                        contentDescription = null,
                        imageVector = Icons.Default.Add
                    )
                }
            }
        },
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
            value = addTodoText.value,
            onValueChange = { addTodoText.value = it },
            singleLine = true,
            label = { Text(stringResource(R.string.create_todo)) },
//            keyboardActions = KeyboardActions
        )
    }
}

@Composable
fun CrossingShops(
    modifier: Modifier = Modifier,
    shops: List<UiShop>,
    onShopClick: (UiShop) -> Unit
) {
    CrossingCard(modifier = modifier) {
        Column {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                text = stringResource(R.string.check_each_shop),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                content = {
                    items(shops.size) { index ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                contentDescription = null,
                                modifier = Modifier
                                    .size(42.dp)
                                    .padding(vertical = 8.dp),
                                bitmap = imageFromResource(
                                    AmbientContext.current.resources,
                                    shops[index].resourceImageId
                                )
                            )
                            Checkbox(
                                checked = shops[index].isVisited,
                                onCheckedChange = { onShopClick(shops[index]) })
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun TurnipPriceList(
    modifier: Modifier = Modifier,
    turnipPrices: UiTurnipPrices,
    onTurnipPriceUpdate: (TurnipPriceType, String) -> Unit
) {
    val turnipAmPrice = remember { mutableStateOf(turnipPrices.amPrice) }
    val turnipPmPrice = remember { mutableStateOf(turnipPrices.pmPrice) }

    CrossingCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.turnip_prices),
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                )
                Image(
                    contentDescription = null,
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
                value = turnipAmPrice.value,
                singleLine = true,
                maxLines = 1,
                label = {
                    Text(
                        text = stringResource(id = R.string.turnip_am_price),
                        fontSize = TextUnit(10)
                    )
                },
                onValueChange = { turnipAmPrice.value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                onImeActionPerformed = { imeAction, softKeyboardController ->
//                    if (imeAction == ImeAction.Done) {
//                        onTurnipPriceUpdate(TurnipPriceType.AM, turnipAmPrice.value)
//                        softKeyboardController?.hideSoftwareKeyboard()
//                    }
//                }
            )
            OutlinedTextField(
                modifier = Modifier.padding(horizontal = 8.dp),
                value = turnipPmPrice.value,
                singleLine = true,
                maxLines = 1,
                label = {
                    Text(
                        text = stringResource(id = R.string.turnip_pm_price),
//                        fontSize = TextUnit.Sp(10)
                    )
                },
                onValueChange = { turnipPmPrice.value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                onImeActionPerformed = { imeAction, softKeyboardController ->
//                    if (imeAction == ImeAction.Done) {
//                        onTurnipPriceUpdate(TurnipPriceType.PM, turnipPmPrice.value)
//                        softKeyboardController?.hideSoftwareKeyboard()
//                    }
//                }
            )
        }
    }
}

@Composable
fun VillagerInteractionsList(
    modifier: Modifier = Modifier,
    villagerInteractions: List<VillagerInteraction>,
    onAddVillagerClicked: (String) -> Unit,
    onVillagerInteractionDeleted: (VillagerInteraction) -> Unit,
    onVillagerGiftClicked: (VillagerInteraction, List<VillagerInteraction>) -> Unit,
    onVillagerTalkedToClicked: (VillagerInteraction, List<VillagerInteraction>) -> Unit,
) {
    CrossingCard(modifier = modifier) {
        Column {
            AnimatedAddVillagerContainer(onAddVillagerClicked)
            Divider()
            LazyColumn(
                content = {
                    itemsIndexed(villagerInteractions) { index, villagerInteraction ->
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .longPressGestureFilter {
                                    onVillagerInteractionDeleted(villagerInteraction)
                                },
                        ) {
                            Text(text = (index + 1).toString())
                            Text(
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .weight(3f),
                                text = villagerInteraction.villagerName,
                            )
                            Image(
                                contentDescription = null,
                                modifier = Modifier
                                    .alpha(if (villagerInteraction.receivedGift) 1f else 0.3f)
                                    .size(24.dp)
                                    .clickable(
                                        onClick = {
                                            onVillagerGiftClicked(
                                                villagerInteraction,
                                                villagerInteractions
                                            )
                                        },
//                                        indication = rememberRipple(bounded = false, radius = 18.dp)
                                    ),
                                bitmap = imageFromResource(
                                    LocalContext.current.resources,
                                    R.drawable.present
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Image(
                                contentDescription = null,
                                modifier = Modifier
                                    .alpha(if (villagerInteraction.talkedTo) 1f else 0.3f)
                                    .size(24.dp)
                                    .clickable(
                                        onClick = {
                                            onVillagerTalkedToClicked(
                                                villagerInteraction,
                                                villagerInteractions
                                            )
                                        },
//                                        indication = rememberRipple(bounded = false, radius = 18.dp)
                                    ),
                                bitmap = imageFromResource(
                                    LocalContext.current.resources,
                                    R.drawable.speech_bubble
                                )
                            )
                        }
                    }
                },
            )
        }
    }
}

@Composable
fun AnimatedAddVillagerContainer(
    onAddVillagerClicked: (String) -> Unit
) {
    val animationState = remember { mutableStateOf(AnimatedContainerState.IDLE) }
    val addVillagerText = remember { mutableStateOf("") }

    AnimatedContainer(
        animationState = animationState,
        nonExpandedContent = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.villagers),
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold)
                )
                IconButton(
                    onClick = {
                        animationState.value =
                            when (animationState.value) {
                                AnimatedContainerState.IDLE -> AnimatedContainerState.PRESSED
                                AnimatedContainerState.PRESSED -> AnimatedContainerState.IDLE
                            }
                    },
                ) {
                    Icon(
                        contentDescription = null,
                        imageVector = Icons.Default.Add
                    )
                }
            }
        }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
            value = addVillagerText.value,
            onValueChange = { addVillagerText.value = it },
            singleLine = true,
            label = { Text(stringResource(R.string.villager_name)) },
//            onImeActionPerformed = { imeAction, _ ->
//                if (imeAction == ImeAction.Done) {
//                    onAddVillagerClicked(addVillagerText.value)
//                    addVillagerText.value = ""
//                    animationState.value = AnimatedContainerState.PRESSED
//                }
//            }
        )
    }
}

@Composable
fun CrossingNotes(
    modifier: Modifier = Modifier,
    notes: String = "",
    onNotesUpdated: (String) -> Unit
) {
    val modifiedNotes = mutableStateOf(notes)

    CrossingCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 125.dp)
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(R.string.notes),
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
            )
            OutlinedTextField(
                value = modifiedNotes.value,
                onValueChange = { modifiedNotes.value = it },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//                onImeActionPerformed = { imeAction, keyboardController ->
//                    if (imeAction == ImeAction.Done) {
//                        onNotesUpdated(modifiedNotes.value)
//                        keyboardController?.hideSoftwareKeyboard()
//                    }
//                }
            )
        }
    }
}