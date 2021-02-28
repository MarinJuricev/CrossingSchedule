package com.example.crossingschedule.feature.auth.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.crossingschedule.R
import com.example.crossingschedule.feature.auth.presentation.model.SignUpError
import com.example.crossingschedule.feature.auth.presentation.model.SignUpViewState

@Composable
fun SignUpComponent(
    signUpViewState: SignUpViewState
) {
    Crossfade(
        targetState = Unit,
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxHeight()
        ) {
            val (signUpTitle, signUpInputFields,
                bottomDecor, createAccountButton) = createRefs()
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
                signUpViewState = signUpViewState
            )
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
                onClick = { }
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
    signUpViewState: SignUpViewState
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            isError = signUpViewState.signUpError is SignUpError.EmailError,
            value = signUpViewState.email,
            onValueChange = {},
            singleLine = true,
            label = { Text(stringResource(R.string.email)) },
        )
//        Text(
//            modifier = Modifier
//                .alpha(emailAlpha)
//                .fillMaxWidth()
//                .height(emailHeight.value),
//            text = (loginViewState.loginError as? LoginError.EmailError)?.emailError ?: "",
//            color = MaterialTheme.colors.error,
//            textAlign = TextAlign.Center,
//        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = signUpViewState.password,
            isError = signUpViewState.signUpError is SignUpError.PasswordError,
            onValueChange = {},
            singleLine = true,
            label = { Text(stringResource(R.string.password)) },
        )
//        Text(
//            modifier = Modifier
//                .alpha(passwordAlpha)
//                .fillMaxWidth()
//                .padding(passwordHeight.value),
//            text = (loginViewState.loginError as? LoginError.PasswordError)?.passwordError
//                ?: "",//TODO Cleaner API for this... this is mega messy
//            color = MaterialTheme.colors.error,
//            textAlign = TextAlign.Center,
//        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = signUpViewState.confirmPassword,
            isError = signUpViewState.signUpError is SignUpError.ConfirmPasswordError,
            onValueChange = {},
            singleLine = true,
            label = { Text(stringResource(R.string.password)) },
        )
    }
}