package com.example.crossingschedule.feature.auth.presentation.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.crossingschedule.R
import com.example.crossingschedule.feature.auth.presentation.LOGIN_TAB_POSITION
import com.example.crossingschedule.feature.auth.presentation.SIGN_UP_TAB_POSITION
import com.example.crossingschedule.feature.auth.presentation.model.AnimatedLoginValidatorState
import com.example.crossingschedule.feature.auth.presentation.model.LoginError
import com.example.crossingschedule.feature.auth.presentation.model.LoginEvent
import com.example.crossingschedule.feature.auth.presentation.model.LoginEvent.*
import com.example.crossingschedule.feature.auth.presentation.model.LoginViewState

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
    onLoginEvent: (LoginEvent) -> Unit,
) {
    Crossfade(
        targetState = loginViewState.navigateToSchedule,
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxHeight()
        ) {
            val (loginImage, appTitle, salesPitch,
                loginInputFields, bottomDecor,
                loginButton, loginProgressBar) = createRefs()
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
                painter = painterResource(id = R.drawable.login_icon),
                contentDescription = null
            )
            LoginInputFields(
                modifier = Modifier
                    .constrainAs(loginInputFields) {
                        top.linkTo(loginImage.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                loginViewState = loginViewState,
                onLoginEvent = onLoginEvent,
            )
            //TODO Add a sign in with Google ?
            if (loginViewState.isLoading) {
                CircularProgressIndicator(modifier = Modifier
                    .size(24.dp)
                    .constrainAs(loginProgressBar) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
            }
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
                onClick = { onLoginEvent(OnLoginClick) }
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    text = stringResource(R.string.login),
                    style = TextStyle(color = MaterialTheme.colors.surface),
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
fun LoginInputFields(
    modifier: Modifier = Modifier,
    loginViewState: LoginViewState,
    onLoginEvent: (LoginEvent) -> Unit,
) {
    var validatorAnimatedStates by
    remember { mutableStateOf(AnimatedLoginValidatorState.NO_ERROR) }

    validatorAnimatedStates = when (loginViewState.loginError) {
        is LoginError.GeneralError -> AnimatedLoginValidatorState.NO_ERROR
        is LoginError.EmailError -> AnimatedLoginValidatorState.EMAIL_ERROR
        is LoginError.PasswordError -> AnimatedLoginValidatorState.PASSWORD_ERROR
        else -> AnimatedLoginValidatorState.NO_ERROR
    }
    val validatorTransition = updateTransition(targetState = validatorAnimatedStates)

    val emailAlpha by validatorTransition.animateFloat(
        transitionSpec = {
            tween(durationMillis = 1000)
        }
    ) {
        when (it) {
            AnimatedLoginValidatorState.EMAIL_ERROR -> 1f
            else -> 0f
        }
    }

    val emailHeight = validatorTransition.animateDp(
        transitionSpec = {
            tween(durationMillis = 1000)
        }
    ) {
        when (it) {
            AnimatedLoginValidatorState.EMAIL_ERROR -> 24.dp
            else -> 0.dp
        }
    }

    val passwordHeight = validatorTransition.animateDp(
        transitionSpec = {
            tween(durationMillis = 1000)
        }
    ) {
        when (it) {
            AnimatedLoginValidatorState.PASSWORD_ERROR -> 12.dp
            else -> 0.dp
        }
    }

    val passwordAlpha by validatorTransition.animateFloat(
        transitionSpec = {
            tween(durationMillis = 1000)
        }
    ) {
        when (it) {
            AnimatedLoginValidatorState.PASSWORD_ERROR -> 1f
            else -> 0f
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            isError = loginViewState.loginError is LoginError.EmailError,
            value = loginViewState.email,
            onValueChange = { onLoginEvent(OnEmailChange(it)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            singleLine = true,
            label = { Text(stringResource(R.string.email)) },
        )
        Text(
            modifier = Modifier
                .alpha(emailAlpha)
                .fillMaxWidth()
                .height(emailHeight.value),
            text = (loginViewState.loginError as? LoginError.EmailError)?.emailError ?: "",
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = loginViewState.password,
            isError = loginViewState.loginError is LoginError.PasswordError,
            onValueChange = { onLoginEvent(OnPasswordChange(it)) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            singleLine = true,
            label = { Text(stringResource(R.string.password)) },
        )
        Text(
            modifier = Modifier
                .alpha(passwordAlpha)
                .fillMaxWidth()
                .padding(passwordHeight.value),
            text = (loginViewState.loginError as? LoginError.PasswordError)?.passwordError
                ?: "",//TODO Cleaner API for this... this is mega messy
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
        )
    }
}