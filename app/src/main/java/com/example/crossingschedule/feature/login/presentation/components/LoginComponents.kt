package com.example.crossingschedule.feature.login.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.R
import com.example.crossingschedule.feature.login.presentation.LOGIN_TAB_POSITION
import com.example.crossingschedule.feature.login.presentation.SIGN_UP_TAB_POSITION

@Composable
fun LoginTab(
    selectedTabPosition: Int,
    onTabClick: (Int) -> Unit
){
    TabRow(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(0.5f),
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.primary,
        selectedTabIndex = selectedTabPosition
    ) {
        Tab(modifier = Modifier
            .padding(8.dp),
            selected = selectedTabPosition == LOGIN_TAB_POSITION,
            onClick = { onTabClick(LOGIN_TAB_POSITION) }
        ) { Text(text = stringResource(R.string.login)) }
        Tab(modifier = Modifier
            .padding(8.dp),
            selected = selectedTabPosition == SIGN_UP_TAB_POSITION,
            onClick = { onTabClick(SIGN_UP_TAB_POSITION) }
        ) {
            Text(text = stringResource(R.string.sign_up))
        }
    }
}