package com.example.crossingschedule.feature.login.presentation

import androidx.lifecycle.ViewModel
import com.example.crossingschedule.feature.login.presentation.model.LoginViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

) : ViewModel() {

    private val _loginViewState = MutableStateFlow(LoginViewState())
    val loginViewState = _loginViewState

    fun onLoginClick(
        email: String,
        password: String
    ) {

    }

}