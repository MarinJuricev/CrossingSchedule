package com.example.crossingschedule.feature.login.presentation.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.crossingschedule.R
import com.example.crossingschedule.feature.login.presentation.LOGIN_TAB_POSITION
import com.example.crossingschedule.feature.login.presentation.SIGN_UP_TAB_POSITION
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
    Crossfade(
        current = Any(),
        animation = tween(durationMillis = 500)
    ) {
        val emailText = remember { mutableStateOf(loginViewState.email) }
        val passwordText = remember { mutableStateOf(loginViewState.password) }

        ConstraintLayout(
            modifier = Modifier.fillMaxHeight()
        ) {
            val (loginImage, appTitle, salesPitch,
                emailTextField, passwordTextField,
                bottomDecor, loginButton) = createRefs()
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
                    AmbientContext.current.resources,
                    R.drawable.login_icon
                ),
                contentDescription = null
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .constrainAs(emailTextField) {
                        top.linkTo(loginImage.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                isErrorValue = loginViewState.loginError is LoginError.EmailError,
                value = emailText.value,
                onValueChange = { emailText.value = it },
                singleLine = true,
                label = { Text(stringResource(R.string.email)) },
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .constrainAs(passwordTextField) {
                        top.linkTo(emailTextField.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                value = passwordText.value,
                isErrorValue = loginViewState.loginError is LoginError.PasswordError,
                onValueChange = { passwordText.value = it },
                singleLine = true,
                label = { Text(stringResource(R.string.password)) },
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
                onClick = { onLoginClick(emailText.value, passwordText.value) }) {
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