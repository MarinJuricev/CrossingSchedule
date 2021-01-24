package com.example.crossingschedule.feature.schedule.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.Dimension
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.R
import com.example.crossingschedule.feature.schedule.presentation.components.*
import com.example.crossingschedule.feature.schedule.presentation.model.ScheduleViewState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SchedulePage(scheduleViewModel: ScheduleViewModel) {
    scheduleViewModel.getActivitiesForDay(0, 0, 0)//TODO ACTUALLY IMPLEMENT MULTIPLE DAYS

    val viewState =
        scheduleViewModel.crossingDailyActivities.observeAsState(ScheduleViewState())
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = snackBarHostState)
    ) {
        Box {
            BackgroundImage(resourceId = R.drawable.home_background)
            if (viewState.value.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                Crossfade(
                    current = viewState.value.isLoading,
                    animation = tween(2000)
                ) {
                    ConstraintLayout {
                        val (checkListText, dateSelector,
                            todoList, notes,
                            rawIngredientRow, shopContainer,
                            turnipPriceList, villagerList) = createRefs()
                        DailyCheckListCard(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .constrainAs(checkListText) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                },
                        )
                        CurrentDateCard(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .constrainAs(dateSelector) {
                                    top.linkTo(parent.top)
                                    start.linkTo(checkListText.end, margin = 24.dp)
                                    end.linkTo(parent.end)
                                },
                            dateOptions = viewState.value.dateOptions,
                            onDateChanged = scheduleViewModel::getActivitiesForDay
                        )
                        RawIngredientRow(
                            modifier = Modifier
                                .constrainAs(rawIngredientRow) {
                                    start.linkTo(checkListText.end, margin = 8.dp)
                                    top.linkTo(dateSelector.bottom)
                                    end.linkTo(parent.end, margin = 8.dp)
                                }
                        )
                        CrossingTodoList(
                            modifier = Modifier
                                .constrainAs(todoList) {
                                    width = Dimension.fillToConstraints
                                    start.linkTo(checkListText.start, margin = 16.dp)
                                    end.linkTo(checkListText.end)
                                    top.linkTo(checkListText.bottom, margin = 16.dp)
                                },
                            todos = viewState.value.crossingTodos,
                            onDoneClick = scheduleViewModel::onTodoItemChanged,
                            onNewTodoCreated = scheduleViewModel::onTodoCreated,
                            onTodoDeleted = scheduleViewModel::onTodoItemDeleted
                        )
                        CrossingShops(
                            modifier = Modifier
                                .constrainAs(shopContainer) {
                                    width = Dimension.fillToConstraints
                                    top.linkTo(rawIngredientRow.bottom, margin = 16.dp)
                                    start.linkTo(dateSelector.start)
                                    end.linkTo(dateSelector.end)
                                },
                            shops = viewState.value.shops,
                            onShopClick = scheduleViewModel::onShopChanged
                        )
                        TurnipPriceList(
                            modifier = Modifier
                                .constrainAs(turnipPriceList) {
                                    width = Dimension.fillToConstraints
                                    top.linkTo(shopContainer.bottom, margin = 16.dp)
                                    start.linkTo(shopContainer.start)
                                    end.linkTo(parent.end, margin = 8.dp)
                                },
                            turnipPrices = viewState.value.turnipPrices,
                            onTurnipPriceUpdate = scheduleViewModel::onTurnipPriceChange
                        )
                        VillagerInteractionsList(
                            modifier = Modifier
                                .constrainAs(villagerList) {
                                    width = Dimension.fillToConstraints
                                    top.linkTo(turnipPriceList.bottom, margin = 16.dp)
                                    start.linkTo(turnipPriceList.start)
                                    end.linkTo(turnipPriceList.end)
                                },
                            villagerInteractions = viewState.value.villagersInteraction,
                            onAddVillagerClicked = scheduleViewModel::onNewVillagerClicked,
                            onVillagerInteractionDeleted = scheduleViewModel::onVillagerInteractionsDeleted,
                            onVillagerGiftClicked = scheduleViewModel::onVillagerInteractionGiftClicked,
                            onVillagerTalkedToClicked = scheduleViewModel::onVillagerInteractionTalkedToClicked
                        )
                        CrossingNotes(
                            modifier = Modifier
                                .constrainAs(notes) {
                                    width = Dimension.fillToConstraints
                                    top.linkTo(todoList.bottom, margin = 16.dp)
                                    start.linkTo(todoList.start)
                                    end.linkTo(todoList.end)
                                },
                            notes = viewState.value.notes,
                            onNotesUpdated = scheduleViewModel::onNotesEdited
                        )
                    }

                }

            }
            if (viewState.value.errorMessage.isNotBlank()) {
                val localizedDismissMessage = stringResource(R.string.dismiss)

                LaunchedEffect(
                    subject = Any(),
                    block = {
                        snackBarHostState.showSnackbar(
                            message = viewState.value.errorMessage,
                            actionLabel = localizedDismissMessage
                        )
                    },
                )
            }
        }
    }
}

