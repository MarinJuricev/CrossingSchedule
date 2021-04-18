package com.example.crossingschedule.feature.islandCreation.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.R
import com.example.crossingschedule.core.ui.components.CrossingCard
import com.example.crossingschedule.feature.islandCreation.presentation.components.HemisphereSelection
import com.example.crossingschedule.feature.islandCreation.presentation.viewmodel.IslandCreationViewModel

const val ISLAND_CREATION_PAGE = "ISLAND_CREATION_PAGE"

@Composable
fun IslandCreationPage(
    islandCreationViewModel: IslandCreationViewModel
) {
    val islandNameState by remember { mutableStateOf("") }

    CrossingCard(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxSize()
            .padding(36.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .size(64.dp),
                painter = painterResource(id = R.drawable.icon_island),
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                text = stringResource(R.string.create_an_island),
                style = MaterialTheme.typography.h2,
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = islandNameState,
                onValueChange = { it -> "" },
                singleLine = true,
                label = { Text(stringResource(R.string.island_name)) },
            )
            HemisphereSelection()
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = islandNameState,
                onValueChange = { it -> "" },
                singleLine = true,
                label = { Text(stringResource(R.string.hemisphere)) },
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                value = islandNameState,
                onValueChange = { it -> "" },
                singleLine = true,
                label = { Text(stringResource(R.string.number_of_villagers)) },
            )
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                OutlinedButton(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomEnd),
                    onClick = { /*TODO*/ }) {
                    Text(
                        text = stringResource(R.string.create),
                        style = TextStyle(color = MaterialTheme.colors.onSurface)
                    )
                }

            }

        }
    }
}