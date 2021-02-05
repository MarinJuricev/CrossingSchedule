package com.example.crossingschedule.feature.login.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.loadVectorResource
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

    Scaffold() {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LoginTab(
                    selectedTabPosition = selectedTabPosition.value,
                    onTabClick = { selectedTabPosition.value = it }
                )
                val image = loadVectorResource(id = R.drawable.person)
                //loadVectorResource will load the vector image asynchronous
                image.resource.resource?.let {
                    Image(
                        modifier = Modifier
                            .padding(top = 16.dp, end = 16.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .size(36.dp)
                            .background(MaterialTheme.colors.primary)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colors.secondary,
                                shape = MaterialTheme.shapes.medium
                            )
                            .shadow(
                                elevation = 4.dp,
                                shape = MaterialTheme.shapes.medium,
                                clip = true
                            ),
                        imageVector = it,
                        alignment = Alignment.BottomEnd,
                        contentDescription = null
                    )
                }
            }
            when (selectedTabPosition.value) {
                LOGIN_TAB_POSITION -> Text("RENDER THE ACTUAL LOGIN FLOW")
                SIGN_UP_TAB_POSITION -> Text("RENDER THE SIGN UP")
                else -> Text("Render some kind of error container")//TODO Add a Crossing Error container
            }
        }
    }
}