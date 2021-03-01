package com.example.crossingschedule.feature.auth.presentation

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.R
import com.example.crossingschedule.core.ui.components.CrossingErrorCard
import com.example.crossingschedule.feature.auth.presentation.components.LoginComponent
import com.example.crossingschedule.feature.auth.presentation.components.LoginTab
import com.example.crossingschedule.feature.auth.presentation.model.LoginError
import com.example.crossingschedule.feature.auth.presentation.model.LoginViewState
import com.example.crossingschedule.feature.auth.presentation.model.SignUpViewState

const val AUTH_PAGE_ROUTE = "AUTH_PAGE_ROUTE"

internal const val LOGIN_TAB_POSITION = 0
internal const val SIGN_UP_TAB_POSITION = 1

@Composable
fun LoginPage(
    navigateToSchedule: () -> Unit,
    loginViewModel: LoginViewModel,
    signUpViewModel: SignUpViewModel,
) {
    val loginViewState by loginViewModel.loginViewState.collectAsState(LoginViewState())
    val signUpViewState by signUpViewModel.signUpViewState.collectAsState(SignUpViewState())
    val snackBarHostState = remember { SnackbarHostState() }

    var selectedTabPosition by remember { mutableStateOf(LOGIN_TAB_POSITION) }

    //TODO Make a better API for this...
    if (loginViewState.navigateToSchedule || signUpViewState.navigateToSchedule) {
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
                    selectedTabPosition = selectedTabPosition,
                    onTabClick = { selectedTabPosition = it }
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
            when (selectedTabPosition) {
                LOGIN_TAB_POSITION -> LoginComponent(
                    loginViewState,
                    loginViewModel::onLoginClick,
                    loginViewModel::onEmailChange,
                    loginViewModel::onPasswordChange
                )
                SIGN_UP_TAB_POSITION -> SignUpComponent(
                    signUpViewState,
                    signUpViewModel::onEmailChange,
                    signUpViewModel::onPasswordChange,
                    signUpViewModel::onConfirmPasswordChange
                )
                else -> CrossingErrorCard(
                    errorMessage = stringResource(id = R.string.unknown_error)
                )
            }
        }
        if (loginViewState.isLoading)
            CircularProgressIndicator(modifier = Modifier.size(128.dp))//TODO Style this...
        if (loginViewState.loginError is LoginError.GeneralError) {
            LaunchedEffect(
                key1 = Any(),
                block = {
                    snackBarHostState.showSnackbar(
                        message = loginViewState.loginError!!.error,
                    )
                },
            )
        }
    }
}