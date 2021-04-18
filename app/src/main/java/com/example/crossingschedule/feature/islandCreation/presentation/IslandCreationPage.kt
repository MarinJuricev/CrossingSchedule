package com.example.crossingschedule.feature.islandCreation.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.R
import com.example.crossingschedule.core.ui.components.CrossingCard
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
            modifier = Modifier
                .fillMaxWidth(0.8f)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = islandNameState,
                onValueChange = { it -> "" },
                singleLine = true,
                label = { Text(stringResource(R.string.island_name)) },
            )
        }
    }

}