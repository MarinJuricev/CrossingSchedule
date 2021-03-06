package com.example.crossingschedule.feature.auth.presentation.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.crossingschedule.R
import com.example.crossingschedule.feature.auth.presentation.SignUpEvent
import com.example.crossingschedule.feature.auth.presentation.SignUpEvent.*
import com.example.crossingschedule.feature.auth.presentation.model.AnimatedSignUpValidatorState.*
import com.example.crossingschedule.feature.auth.presentation.model.SignUpError.*
import com.example.crossingschedule.feature.auth.presentation.model.SignUpViewState

@Composable
fun SignUpComponent(
    signUpViewState: SignUpViewState,
    onSignUpEvent: (SignUpEvent) -> Unit,
) {
    Crossfade(
        targetState = Unit,
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxHeight()
        ) {
            val (signUpTitle, signUpInputFields,
                bottomDecor, createAccountButton,
                signUpProgressBar) = createRefs()
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(signUpTitle) {
                        top.linkTo(parent.top, margin = 32.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                text = stringResource(id = R.string.sign_up_title),
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center
            )
            SignUpInputFields(
                modifier = Modifier.constrainAs(signUpInputFields) {
                    top.linkTo(signUpTitle.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                signUpViewState = signUpViewState,
                onSignUpEvent = onSignUpEvent,
            )
            if (signUpViewState.isLoading) {
                CircularProgressIndicator(modifier = Modifier
                    .constrainAs(signUpProgressBar) {
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
                    .constrainAs(createAccountButton) {
                        end.linkTo(parent.end, margin = 16.dp)
                        top.linkTo(bottomDecor.top)
                        bottom.linkTo(bottomDecor.top)
                    },
                onClick = { onSignUpEvent(OnCreateAccountClick) }
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    text = stringResource(R.string.create_account),
                    style = TextStyle(color = MaterialTheme.colors.surface),
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
fun SignUpInputFields(
    modifier: Modifier = Modifier,
    signUpViewState: SignUpViewState,
    onSignUpEvent: (SignUpEvent) -> Unit
) {
    var validatorAnimatedStates by remember { mutableStateOf(NO_ERROR) }

    validatorAnimatedStates = when (signUpViewState.signUpError) {
        is UserNameError -> USERNAME_ERROR
        is EmailError -> EMAIL_ERROR
        is PasswordError -> PASSWORD_ERROR
        is ConfirmPasswordError -> CONFIRM_PASSWORD_ERROR
        else -> NO_ERROR
    }

    val validatorTransition = updateTransition(
        label = "validatorTransition",
        targetState = validatorAnimatedStates,
    )

    val userNameAlpha by validatorTransition.animateFloat(
        label = "userName",
        transitionSpec = {
            tween(durationMillis = 1000)
        },
    ) {
        when (it) {
            USERNAME_ERROR -> 1f
            else -> 0f
        }
    }

    val usernameHeight = validatorTransition.animateDp(
        label = "usernameHeight",
        transitionSpec = {
            tween(durationMillis = 1000)
        },
    ) {
        when (it) {
            USERNAME_ERROR -> 24.dp
            else -> 0.dp
        }
    }

    val emailAlpha by validatorTransition.animateFloat(
        label = "emailAlpha",
        transitionSpec = {
            tween(durationMillis = 1000)
        },
    ) {
        when (it) {
            EMAIL_ERROR -> 1f
            else -> 0f
        }
    }

    val emailHeight = validatorTransition.animateDp(
        label = "emailHeight",
        transitionSpec = {
            tween(durationMillis = 1000)
        },
    ) {
        when (it) {
            EMAIL_ERROR -> 24.dp
            else -> 0.dp
        }
    }

    val passwordAlpha by validatorTransition.animateFloat(
        label = "passwordAlpha",
        transitionSpec = {
            tween(durationMillis = 1000)
        },
    ) {
        when (it) {
            PASSWORD_ERROR -> 1f
            else -> 0f
        }
    }

    val passwordHeight = validatorTransition.animateDp(
        label = "passwordHeight",
        transitionSpec = {
            tween(durationMillis = 1000)
        },
    ) {
        when (it) {
            PASSWORD_ERROR -> 24.dp
            else -> 0.dp
        }
    }

    val confirmPasswordAlpha by validatorTransition.animateFloat(
        label = "confirmPasswordAlpha",
        transitionSpec = {
            tween(durationMillis = 1000)
        },
    ) {
        when (it) {
            CONFIRM_PASSWORD_ERROR -> 1f
            else -> 0f
        }
    }

    val confirmPasswordHeight = validatorTransition.animateDp(
        label = "confirmPasswordHeight",
        transitionSpec = {
            tween(durationMillis = 1000)
        },
    ) {
        when (it) {
            CONFIRM_PASSWORD_ERROR -> 24.dp
            else -> 0.dp
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
            isError = signUpViewState.signUpError is UserNameError,
            value = signUpViewState.username,
            onValueChange = { onSignUpEvent(OnUsernameChange(it)) },
            singleLine = true,
            label = { Text(stringResource(R.string.username)) },
        )
        Text(
            modifier = Modifier
                .alpha(userNameAlpha)
                .fillMaxWidth()
                .height(usernameHeight.value),
            text = (signUpViewState.signUpError as? UserNameError)?.userNameError ?: "",
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            isError = signUpViewState.signUpError is EmailError,
            value = signUpViewState.email,
            onValueChange = { onSignUpEvent(OnEmailChange(it)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            singleLine = true,
            label = { Text(stringResource(R.string.email)) },
        )
        Text(
            modifier = Modifier
                .alpha(emailAlpha)
                .fillMaxWidth()
                .height(emailHeight.value),
            text = (signUpViewState.signUpError as? EmailError)?.emailError ?: "",
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = signUpViewState.password,
            isError = signUpViewState.signUpError is PasswordError,
            onValueChange = { onSignUpEvent(OnPasswordChange(it)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            label = { Text(stringResource(R.string.password)) },
        )
        Text(
            modifier = Modifier
                .alpha(passwordAlpha)
                .fillMaxWidth()
                .padding(passwordHeight.value),
            text = (signUpViewState.signUpError as? PasswordError)?.passwordError
                ?: "",//TODO Cleaner API for this... this is mega messy
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = signUpViewState.confirmPassword,
            isError = signUpViewState.signUpError is ConfirmPasswordError,
            onValueChange = { onSignUpEvent(OnConfirmPasswordChange(it)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            label = { Text(stringResource(R.string.confirm_password)) },
        )
        Text(
            modifier = Modifier
                .alpha(confirmPasswordAlpha)
                .fillMaxWidth()
                .padding(confirmPasswordHeight.value),
            text = (signUpViewState.signUpError as? ConfirmPasswordError)?.passwordError
                ?: "",//TODO Cleaner API for this... this is mega messy
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
        )
    }
}