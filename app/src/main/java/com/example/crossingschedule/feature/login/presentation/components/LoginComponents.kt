package com.example.crossingschedule.feature.login.presentation.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.R
import com.example.crossingschedule.feature.login.presentation.LOGIN_TAB_POSITION
import com.example.crossingschedule.feature.login.presentation.SIGN_UP_TAB_POSITION

@Composable
fun LoginTab(
    selectedTabPosition: Int,
    onTabClick: (Int) -> Unit
) {
    TabRow(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(0.5f),
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.primary,
        selectedTabIndex = selectedTabPosition
    ) {
        Tab(
            selected = selectedTabPosition == LOGIN_TAB_POSITION,
            onClick = { onTabClick(LOGIN_TAB_POSITION) },
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 8.dp
            )
        ) { Text(text = stringResource(R.string.login)) }
        Tab(
            selected = selectedTabPosition == SIGN_UP_TAB_POSITION,
            onClick = { onTabClick(SIGN_UP_TAB_POSITION) },
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 8.dp
            )
        ) {
            Text(text = stringResource(R.string.sign_up))
        }
    }
}

@Composable
fun LoginComponent() {
    Crossfade(
        current = Any(),
        animation = tween(durationMillis = 500)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Spacer(Modifier.height(64.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.login_sales_pitch),
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center
            )
        }
    }
}