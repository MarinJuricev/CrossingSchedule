package com.example.crossingschedule.feature.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.login.domain.usecase.PerformLogin
import com.example.crossingschedule.feature.login.presentation.model.LoginError
import com.example.crossingschedule.feature.login.presentation.model.LoginViewState
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

    fun onLoginClick(
        email: String,
        password: String
    ) {
        loginViewState.value = loginViewState.value.copy(email = email, password = password)

        viewModelScope.launch {
            when (val result = performLogin(email, password)) {
                is Either.Right -> loginViewState.value =
                    loginViewState.value.copy(navigateToSchedule = true)
                is Either.Left -> loginViewState.value =
                    loginViewState.value.copy(loginError = failureToLoginErrorMapper.map(result.error))
            }
        }
    }

}