package com.example.crossingschedule.feature.login.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.R
import com.example.crossingschedule.feature.login.presentation.components.LoginTab

const val LOGIN_PAGE_ROUTE = "LOGIN_PAGE"

internal const val LOGIN_TAB_POSITION = 0
internal const val SIGN_UP_TAB_POSITION = 1

@Composable
fun LoginPage(
    navigateToSchedule: () -> Unit
) {
    val selectedTabPosition = mutableStateOf(LOGIN_TAB_POSITION)

    Scaffold {
        Column {
            Row() {
                LoginTab(
                    selectedTabPosition = selectedTabPosition.value,
                    onTabClick = { selectedTabPosition.value = it }
                )
                Image(
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp),
                    contentScale = ContentScale.FillBounds,
                    bitmap = imageFromResource(
                        AmbientContext.current.resources,
                        R.drawable.daisy_mae
                    )
                )

            }
            when (selectedTabPosition.value) {
                LOGIN_TAB_POSITION -> Text("RENDER THE ACTUAL LOGIN FLOW")
                SIGN_UP_TAB_POSITION -> Text("RENDER THE SIGN UP")
                else -> Text("Render some kind of error container")//TODO Add a Crossing Error container
            }
        }
    }
}