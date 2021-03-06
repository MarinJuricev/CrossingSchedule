package com.example.crossingschedule.feature.auth.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.crossingschedule.core.BaseViewModel
import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.Either.*
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.auth.domain.model.AuthFailure
import com.example.crossingschedule.feature.auth.domain.usecase.CreateAccount
import com.example.crossingschedule.feature.auth.presentation.SignUpEvent
import com.example.crossingschedule.feature.auth.presentation.SignUpEvent.*
import com.example.crossingschedule.feature.auth.presentation.model.SignUpError
import com.example.crossingschedule.feature.auth.presentation.model.SignUpViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createAccount: CreateAccount,
    private val authFailureToSignUpErrorMapper: Mapper<SignUpError, AuthFailure>
) : BaseViewModel<SignUpEvent>() {

    private val _signUpViewState = MutableStateFlow(SignUpViewState())
    val signUpViewState: StateFlow<SignUpViewState> = _signUpViewState

    override fun onEvent(event: SignUpEvent) {
        when (event) {
            is OnEmailChange -> onEmailChange(event.newEmail)
            is OnUsernameChange -> onUsernameChange(event.newUsername)
            is OnConfirmPasswordChange -> onConfirmPasswordChange(event.newConfirmPassword)
            is OnPasswordChange -> onPasswordChange((event.newPassword))
            OnCreateAccountClick -> onCreateAccountClick()
        }
    }

    private fun onEmailChange(newEmail: String) {
        _signUpViewState.value =
            _signUpViewState.value.copy(
                email = newEmail
            )
    }

    private fun onPasswordChange(newPassword: String) {
        _signUpViewState.value =
            _signUpViewState.value.copy(
                password = newPassword
            )
    }

    private fun onConfirmPasswordChange(newConfirmPassword: String) {
        _signUpViewState.value =
            _signUpViewState.value.copy(
                confirmPassword = newConfirmPassword
            )
    }

    private fun onUsernameChange(newUsername: String) {
        _signUpViewState.value =
            _signUpViewState.value.copy(
                username = newUsername
            )
    }

    private fun onCreateAccountClick() {
        triggerIsLoading()
        val viewState = signUpViewState.value

        viewModelScope.launch {
            when (val result =
                createAccount(
                    username = viewState.username,
                    email = viewState.email,
                    password = viewState.password,
                    confirmPassword = viewState.confirmPassword,
                )
            ) {
                is Right -> _signUpViewState.value =
                    _signUpViewState.value.copy(
                        navigateToSchedule = true,
                        signUpError = null,
                        isLoading = false
                    )
                is Left -> _signUpViewState.value =
                    _signUpViewState.value.copy(
                        signUpError = authFailureToSignUpErrorMapper.map(result.error),
                        isLoading = false
                    )
            }
        }
    }

    private fun triggerIsLoading() {
        _signUpViewState.value =
            _signUpViewState.value.copy(
                isLoading = true
            )
    }
}