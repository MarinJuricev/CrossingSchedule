package com.example.crossingschedule.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.Dimension
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.R
import com.example.crossingschedule.domain.model.CrossingTodo
import com.example.crossingschedule.presentation.home.components.BackgroundImage
import com.example.crossingschedule.presentation.home.components.CrossingTodoList
import com.example.crossingschedule.presentation.home.components.RawIngredientRow
import com.example.crossingschedule.ui.crossingTypography

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

                Card(
                        modifier = Modifier
                                .padding(vertical = 16.dp)
                                .constrainAs(checkListText) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                },
                        elevation = 4.dp,
                        shape = RoundedCornerShape(topRight = 16.dp, bottomRight = 16.dp),
                ) {
                    Text(
                            modifier = Modifier
                                    .padding(start = 24.dp, top = 8.dp, bottom = 8.dp, end = 16.dp),
                            text = "Daily Checklist",
                            style = crossingTypography.h2,
                    )
                }
                Card(
                        modifier = Modifier
                                .padding(vertical = 16.dp)
                                .constrainAs(dateSelector) {
                                    top.linkTo(parent.top)
                                    start.linkTo(checkListText.end, margin = 24.dp)
                                    end.linkTo(parent.end)
                                },
                        elevation = 4.dp,
                        shape = RoundedCornerShape(16.dp),
                ) {
                    Text(
                            modifier = Modifier
                                    .padding(start = 8.dp, top = 4.dp, bottom = 4.dp, end = 16.dp),
                            text = "Date: 18.12.2020",
                            style = crossingTypography.h6.copy(fontWeight = FontWeight.Bold),
                    )
                }
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
                        )//TODO REMOVE HARD CODED DATA
                )
            }
        }
    }
}

