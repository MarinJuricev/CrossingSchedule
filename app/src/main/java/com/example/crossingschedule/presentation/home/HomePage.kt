package com.example.crossingschedule.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.Dimension
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.R
import com.example.crossingschedule.domain.model.CrossingTodo
import com.example.crossingschedule.presentation.home.components.*

@Composable
fun HomePage() {
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
                )
                RawIngredientRow(Modifier
                        .constrainAs(rawIngredientRow) {
                            start.linkTo(checkListText.end, margin = 8.dp)
                            top.linkTo(dateSelector.bottom)
                            end.linkTo(parent.end, margin = 8.dp)
                        }
                )
                CrossingTodoList(Modifier
                        .constrainAs(todoList) {
                            width = Dimension.fillToConstraints
                            start.linkTo(checkListText.start, margin = 16.dp)
                            end.linkTo(checkListText.end)
                            top.linkTo(checkListText.bottom, margin = 16.dp)
                        },
                        listOf(
                                CrossingTodo("Prvi", false),
                                CrossingTodo("Drugi", true),
                                CrossingTodo("Treci", false),
                                CrossingTodo("Cevrti", true),
                                CrossingTodo("Peti", false),
                                CrossingTodo("Sesti", true),
                                CrossingTodo("Sedmi", false),
                        )//TODO REMOVE HARD CODED DATA
                )
                CrossingShops(
                        Modifier
                                .constrainAs(shopContainer) {
                                    width = Dimension.fillToConstraints
                                    top.linkTo(rawIngredientRow.bottom, margin = 16.dp)
                                    start.linkTo(dateSelector.start)
                                    end.linkTo(dateSelector.end)
                                },
                )
                TurnipPriceList(
                        Modifier
                                .constrainAs(turnipPriceList) {
                                    width = Dimension.fillToConstraints
                                    top.linkTo(shopContainer.bottom, margin = 16.dp)
                                    start.linkTo(shopContainer.start)
                                    end.linkTo(parent.end, margin = 8.dp)
                                },
                )
            }
        }
    }
}

