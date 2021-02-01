package com.example.crossingschedule.feature.login.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

const val LOGIN_PAGE_ROUTE = "LOGIN_PAGE"

@Composable
fun LoginPage(
    navigateToSchedule: () -> Unit
) {
    Scaffold {
        Row {
            Text("Crossing Schedule")
            OutlinedTextField(
                value = "",
                label = { Text("UserName") },
                onValueChange = { /*TODO*/ }
            )
            OutlinedTextField(
                value = "",
                label = { Text("Password") },
                onValueChange = { /*TODO*/ }
            )
            Button(onClick = navigateToSchedule) {
                Text("Click me")
            }
        }
    }
}