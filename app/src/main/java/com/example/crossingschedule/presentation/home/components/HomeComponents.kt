package com.example.crossingschedule.presentation.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.R
import com.example.crossingschedule.domain.model.CrossingTodo

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
            LazyColumn(
                    modifier = Modifier.padding(bottom = 8.dp),
                    content = {
                        items(todos) { todo ->
                            Row(modifier = Modifier
                                    .padding(4.dp)) {
                                Checkbox(checked = todo.isDone, onCheckedChange = { })
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = todo.message)
                            }
                        }
                    }
            )
            Divider()
            IconButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { }) {
                Row(
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 8.dp),
                        horizontalArrangement = Arrangement.End,
                ) {
                    Text(stringResource(R.string.add_todo))
                    Icon(imageVector = Icons.Default.Add)
                }
            }
        }
    }
}
