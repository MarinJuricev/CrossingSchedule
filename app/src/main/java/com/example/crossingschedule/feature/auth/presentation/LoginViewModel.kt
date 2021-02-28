package com.example.crossingschedule.feature.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.auth.domain.usecase.PerformLogin
import com.example.crossingschedule.feature.auth.presentation.model.LoginError
import com.example.crossingschedule.feature.auth.presentation.model.LoginViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val performLogin: PerformLogin,
    private val failureToLoginErrorMapper: Mapper<LoginError, Failure>
) : ViewModel() {

    private val _loginViewState = MutableStateFlow(LoginViewState())
    val loginViewState = _loginViewState

    fun onEmailChange(newEmail: String) {
        loginViewState.value =
            loginViewState.value.copy(
                email = newEmail
            )
    }

    fun onPasswordChange(newPassword: String) {
        loginViewState.value =
            loginViewState.value.copy(
                password = newPassword
            )
    }

    fun onLoginClick() {
        triggerIsLoading()
        val viewState = loginViewState.value

        viewModelScope.launch {
            when (val result = performLogin(viewState.email, viewState.password)) {
                is Either.Right -> loginViewState.value =
                    loginViewState.value.copy(
                        navigateToSchedule = true,
                        loginError = null,
                        isLoading = false
                    )
                is Either.Left -> loginViewState.value =
                    loginViewState.value.copy(
                        loginError = failureToLoginErrorMapper.map(result.error),
                        isLoading = false
                    )
            }
        }
    }

    private fun triggerIsLoading() {
        loginViewState.value =
            loginViewState.value.copy(
                isLoading = true
            )
    }
}