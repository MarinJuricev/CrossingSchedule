package com.example.crossingschedule.feature.islandCreation.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.R

@Composable
fun HemisphereSelection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text =stringResource(id = R.string.hemisphere_selection),
            style = MaterialTheme.typography.h4,
        )
        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = false,
                    onClick = {},
                )
                Spacer(modifier = Modifier.padding(start = 4.dp))
                Text(
                    text = stringResource(R.string.north),
                    style = MaterialTheme.typography.body1,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = false,
                    onClick = {},
                )
                Spacer(modifier = Modifier.padding(start = 4.dp))
                Text(
                    text = stringResource(R.string.south),
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    }
}