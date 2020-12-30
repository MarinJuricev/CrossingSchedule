package com.example.crossingschedule.presentation.core.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CrossingCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Card(
            modifier = modifier,
            elevation = 4.dp,
            shape = RoundedCornerShape(16.dp),
    ) {
        content()
    }
}