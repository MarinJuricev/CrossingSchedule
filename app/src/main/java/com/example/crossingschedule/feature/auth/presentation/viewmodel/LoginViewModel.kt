package com.example.crossingschedule.feature.auth.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.crossingschedule.core.BaseViewModel
import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.Either.*
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure
import com.example.crossingschedule.feature.auth.domain.usecase.PerformLogin
import com.example.crossingschedule.feature.auth.presentation.model.LoginError
import com.example.crossingschedule.feature.auth.presentation.model.LoginEvent
import com.example.crossingschedule.feature.auth.presentation.model.LoginEvent.*
import com.example.crossingschedule.feature.auth.presentation.model.LoginViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val performLogin: PerformLogin,
    private val authFailureToLoginErrorMapper: Mapper<LoginError, AuthFailure>
) : BaseViewModel<LoginEvent>() {

    private val _loginViewState = MutableStateFlow(LoginViewState())
    val loginViewState = _loginViewState

    override fun onEvent(event: LoginEvent) {
        when(event){
            is OnEmailChange -> onEmailChange(event.newEmail)
            is OnPasswordChange -> onPasswordChange(event.newPassword)
            OnLoginClick -> onLoginClick()
        }
    }

    private fun onEmailChange(newEmail: String) {
        loginViewState.value =
            loginViewState.value.copy(
                email = newEmail
            )
    }

    private fun onPasswordChange(newPassword: String) {
        loginViewState.value =
            loginViewState.value.copy(
                password = newPassword
            )
    }

    private fun onLoginClick() {
        triggerIsLoading()
        val viewState = loginViewState.value

        viewModelScope.launch {
            when (val result = performLogin(viewState.email, viewState.password)) {
                is Right -> loginViewState.value =
                    loginViewState.value.copy(
                        navigateToSchedule = true,
                        loginError = null,
                        isLoading = false
                    )
                is Left -> loginViewState.value =
                    loginViewState.value.copy(
                        loginError = authFailureToLoginErrorMapper.map(result.error),
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