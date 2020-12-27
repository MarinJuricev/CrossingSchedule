package com.example.crossingschedule.presentation.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.R
import com.example.crossingschedule.domain.model.CrossingTodo
import com.example.crossingschedule.ui.crossingTypography

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
fun CurrentDateCard(modifier: Modifier) {
    Card(
            modifier = modifier,
            elevation = 4.dp,
            shape = MaterialTheme.shapes.large,
    ) {
        Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                text = "Date: 18.12.2020", // TODO Actually set the current date.
                textAlign = TextAlign.Center,
                style = crossingTypography.h6.copy(fontWeight = FontWeight.Bold),
        )
    }
}

@Composable
fun RawIngredientRow(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
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

@Composable
fun CrossingTodoList(
        modifier: Modifier = Modifier,
        todos: List<CrossingTodo>
) {
    Card(
            shape = MaterialTheme.shapes.large,
            modifier = modifier
    ) {
        Column {
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                        modifier = Modifier.padding(start = 16.dp),
                        textAlign = TextAlign.Center,
                        text = stringResource(id = R.string.add_todo),
                )
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Default.Add)
                }
            }
            Divider()
            LazyColumn(
                    modifier = Modifier
                            .padding(bottom = 8.dp, top = 8.dp),
                    content = {
                        items(todos) { todo ->
                            Row(
                                    modifier = Modifier.padding(4.dp),
                                    verticalAlignment = Alignment.CenterVertically,

                                    ) {
                                Checkbox(checked = todo.isDone, onCheckedChange = { })
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
fun CrossingShops(modifier: Modifier = Modifier) {
    Card(
            modifier = modifier,
            shape = MaterialTheme.shapes.large,
    ) {
        Column {
            Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    text = stringResource(R.string.check_each_shop),
                    textAlign = TextAlign.Center,
                    style = crossingTypography.h6.copy(fontWeight = FontWeight.Bold),
            )
            Row(
                    modifier = Modifier
                            .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
            ) {
                Image(
                        modifier = Modifier
                                .size(42.dp)
                                .padding(top = 4.dp),
                        bitmap = imageFromResource(
                                AmbientContext.current.resources,
                                R.drawable.able_sisters_shop_icon
                        )
                )
                Image(
                        modifier = Modifier
                                .size(42.dp)
                                .padding(top = 4.dp),
                        bitmap = imageFromResource(
                                AmbientContext.current.resources,
                                R.drawable.nooks_cranny_shop_icon
                        )
                )
                Image(
                        modifier = Modifier
                                .size(42.dp)
                                .padding(top = 4.dp),
                        bitmap = imageFromResource(
                                AmbientContext.current.resources,
                                R.drawable.museum_shop_icon
                        )
                )
            }
        }
    }

}