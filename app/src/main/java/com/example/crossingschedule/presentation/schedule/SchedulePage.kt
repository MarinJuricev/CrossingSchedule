package com.example.crossingschedule.presentation.schedule

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.Dimension
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.R
import com.example.crossingschedule.presentation.schedule.components.*
import com.example.crossingschedule.presentation.schedule.model.ScheduleViewState

@Composable
fun HomePage(scheduleViewModel: ScheduleViewModel) {
    scheduleViewModel.getActivitiesForDay("TEST_DAY")//TODO ACTUALLY IMPLEMENT MULTIPLE DAYS

    val viewState =
        scheduleViewModel.crossingDailyActivities.observeAsState(ScheduleViewState())

    Scaffold {
        Box {
            BackgroundImage(resourceId = R.drawable.home_background)
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
                    dateToDisplay = viewState.value.currentDate
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
                    todos = viewState.value.crossingTodos
                )
                CrossingShops(
                    modifier = Modifier
                        .constrainAs(shopContainer) {
                            width = Dimension.fillToConstraints
                            top.linkTo(rawIngredientRow.bottom, margin = 16.dp)
                            start.linkTo(dateSelector.start)
                            end.linkTo(dateSelector.end)
                        },
                    shops = viewState.value.shops
                )
                TurnipPriceList(
                    modifier = Modifier
                        .constrainAs(turnipPriceList) {
                            width = Dimension.fillToConstraints
                            top.linkTo(shopContainer.bottom, margin = 16.dp)
                            start.linkTo(shopContainer.start)
                            end.linkTo(parent.end, margin = 8.dp)
                        },
                )
                VillagerInteractionsList(
                    modifier = Modifier
                        .constrainAs(villagerList) {
                            width = Dimension.fillToConstraints
                            top.linkTo(turnipPriceList.bottom, margin = 16.dp)
                            start.linkTo(turnipPriceList.start)
                            end.linkTo(turnipPriceList.end)
                        },
                    villagerInteractions = viewState.value.villagersInteraction
                )
                Notes(
                    modifier = Modifier
                        .constrainAs(notes) {
                            width = Dimension.fillToConstraints
                            top.linkTo(todoList.bottom, margin = 16.dp)
                            start.linkTo(todoList.start)
                            end.linkTo(todoList.end)
                        },
                    notes = viewState.value.notes
                )
            }
        }
    }
}

