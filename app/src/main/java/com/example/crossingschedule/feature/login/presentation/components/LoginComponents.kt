package com.example.crossingschedule.feature.login.presentation.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.crossingschedule.R
import com.example.crossingschedule.feature.login.presentation.LOGIN_TAB_POSITION
import com.example.crossingschedule.feature.login.presentation.SIGN_UP_TAB_POSITION
import com.example.crossingschedule.feature.login.presentation.model.AnimatedValidatorState
import com.example.crossingschedule.feature.login.presentation.model.LoginError
import com.example.crossingschedule.feature.login.presentation.model.LoginViewState

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
fun LoginComponent(
    loginViewState: LoginViewState,
    onLoginClick: (String, String) -> Unit
) {
//    Crossfade(
//        targetState = Any(),
//    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxHeight()
        ) {
            val (loginImage, appTitle, salesPitch,
                loginInputFields, bottomDecor,
                loginButton) = createRefs()
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(appTitle) {
                        top.linkTo(parent.top, margin = 32.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .constrainAs(salesPitch) {
                        top.linkTo(appTitle.bottom)
                        start.linkTo(appTitle.start)
                        end.linkTo(appTitle.end)
                    },
                text = stringResource(R.string.login_sales_pitch),
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center
            )
            Image(
                modifier = Modifier
                    .padding(16.dp)
                    .constrainAs(loginImage) {
                        top.linkTo(salesPitch.bottom)
                        start.linkTo(salesPitch.start)
                        end.linkTo(salesPitch.end)
                    },
                bitmap = imageFromResource(
                    LocalContext.current.resources,
                    R.drawable.login_icon
                ),
                contentDescription = null
            )
            LoginInputFields(
                modifier = Modifier
                .constrainAs(loginInputFields) {
                    top.linkTo(loginImage.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                loginViewState = loginViewState
            )
            //TODO Add a sign in with Google ?
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colors.secondary)
                    .height(64.dp)
                    .fillMaxWidth()
                    .constrainAs(bottomDecor) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
            )
            OutlinedButton(
                modifier = Modifier
                    .constrainAs(loginButton) {
                        end.linkTo(parent.end, margin = 16.dp)
                        top.linkTo(bottomDecor.top)
                        bottom.linkTo(bottomDecor.top)
                    },
                onClick = { onLoginClick("emailText.value", "") }) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    text = stringResource(R.string.login),
                    style = TextStyle(color = MaterialTheme.colors.surface),
                    color = MaterialTheme.colors.primary
                )
            }
        }
}

@Composable
fun LoginInputFields(
    modifier: Modifier = Modifier,
    loginViewState: LoginViewState
) {
    val emailText = remember { mutableStateOf(loginViewState.email) }
    val passwordText = remember { mutableStateOf(loginViewState.password) }
    val validatorAnimatedStates =
        remember { mutableStateOf(AnimatedValidatorState.NO_ERROR) }

    validatorAnimatedStates.value = when (loginViewState.loginError) {
        is LoginError.GeneralError -> AnimatedValidatorState.NO_ERROR
        is LoginError.EmailError -> AnimatedValidatorState.EMAIL_ERROR
        is LoginError.PasswordError -> AnimatedValidatorState.PASSWORD_ERROR
        else -> AnimatedValidatorState.NO_ERROR
    }
    val validatorTransition = updateTransition(targetState = validatorAnimatedStates)

    val emailAlpha by validatorTransition.animateFloat(
        transitionSpec = {
            tween(durationMillis = 1000)
        }
    ) {
        when (it.value) {
            AnimatedValidatorState.NO_ERROR -> 0f
            AnimatedValidatorState.PASSWORD_ERROR -> 0f
            AnimatedValidatorState.EMAIL_ERROR -> 1f
        }
    }

    val emailHeight = validatorTransition.animateDp(
        transitionSpec = {
            tween(durationMillis = 1000)
        }
    ) {
        when (it.value) {
            AnimatedValidatorState.NO_ERROR -> 0.dp
            AnimatedValidatorState.PASSWORD_ERROR -> 0.dp
            AnimatedValidatorState.EMAIL_ERROR -> 24.dp
        }
    }

    Column(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            isErrorValue = loginViewState.loginError is LoginError.EmailError,
            value = emailText.value,
            onValueChange = { emailText.value = it },
            singleLine = true,
            label = { Text(stringResource(R.string.email)) },
        )
        if(loginViewState.loginError is LoginError.EmailError){
            Text(
                modifier = Modifier
                    .alpha(emailAlpha)
                    .height(emailHeight.value),
                text = loginViewState.loginError.emailError,
            )
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = passwordText.value,
            isErrorValue = loginViewState.loginError is LoginError.PasswordError,
            onValueChange = { passwordText.value = it },
            singleLine = true,
            label = { Text(stringResource(R.string.password)) },
        )
    }
}