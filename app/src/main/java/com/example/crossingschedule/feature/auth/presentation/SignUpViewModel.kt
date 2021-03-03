package com.example.crossingschedule.feature.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.auth.domain.usecase.CreateAccount
import com.example.crossingschedule.feature.auth.presentation.model.SignUpError
import com.example.crossingschedule.feature.auth.presentation.model.SignUpViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createAccount: CreateAccount,
    private val failureToSignUpErrorMapper: Mapper<SignUpError, Failure>
) : ViewModel() {

    private val _signUpViewState = MutableStateFlow(SignUpViewState())
    val signUpViewState = _signUpViewState

    fun onEmailChange(newEmail: String) {
        _signUpViewState.value =
            _signUpViewState.value.copy(
                email = newEmail
            )
    }

    fun onPasswordChange(newPassword: String) {
        _signUpViewState.value =
            _signUpViewState.value.copy(
                password = newPassword
            )
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        _signUpViewState.value =
            _signUpViewState.value.copy(
                confirmPassword = newConfirmPassword
            )
    }

    fun onCreateAccountClick() {
        triggerIsLoading()
        val viewState = signUpViewState.value

        viewModelScope.launch {
            when (val result =
                createAccount(viewState.email, viewState.password, viewState.confirmPassword)) {
                is Either.Right -> _signUpViewState.value =
                    _signUpViewState.value.copy(
                        navigateToSchedule = true,
                        signUpError = null,
                        isLoading = false
                    )
                is Either.Left -> _signUpViewState.value =
                    _signUpViewState.value.copy(
                        signUpError = failureToSignUpErrorMapper.map(result.error),
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