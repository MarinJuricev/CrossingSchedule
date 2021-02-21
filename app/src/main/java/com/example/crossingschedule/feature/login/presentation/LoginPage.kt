package com.example.crossingschedule.feature.login.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.R
import com.example.crossingschedule.feature.login.presentation.components.LoginComponent
import com.example.crossingschedule.feature.login.presentation.components.LoginTab
import com.example.crossingschedule.feature.login.presentation.model.LoginViewState

const val LOGIN_PAGE_ROUTE = "LOGIN_PAGE"

internal const val LOGIN_TAB_POSITION = 0
internal const val SIGN_UP_TAB_POSITION = 1

@Composable
fun LoginPage(
    navigateToSchedule: () -> Unit,
    loginViewModel: LoginViewModel,
) {
    val viewState = loginViewModel.loginViewState.collectAsState(LoginViewState())
    val selectedTabPosition = mutableStateOf(LOGIN_TAB_POSITION)
    val snackBarHostState = remember { SnackbarHostState() }

    //TODO Make a better API for this...
    if (viewState.value.navigateToSchedule) {
        navigateToSchedule()
    }

    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = snackBarHostState)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LoginTab(
                    selectedTabPosition = selectedTabPosition.value,
                    onTabClick = { selectedTabPosition.value = it }
                )
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
                    painter = painterResource(id = R.drawable.person),
                    alignment = Alignment.BottomEnd,
                    contentDescription = null
                )
            }
        }
        when (selectedTabPosition.value) {
            LOGIN_TAB_POSITION -> LoginComponent(viewState.value, loginViewModel::onLoginClick)
            SIGN_UP_TAB_POSITION -> Text("RENDER THE SIGN UP")
            else -> Text("Render some kind of error container")//TODO Add a Crossing Error container
        }
        if (viewState.value.isLoading)
            CircularProgressIndicator(modifier = Modifier.size(128.dp))//TODO Style this...
        if (viewState.value.loginError != null) {
            LaunchedEffect(
                key1 = Any(),
                block = {
                    snackBarHostState.showSnackbar(
                        message = viewState.value.loginError!!.error,
                    )
                },
            )
        }
    }
}