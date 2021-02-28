package com.example.crossingschedule.feature.auth.presentation

import androidx.lifecycle.ViewModel
import com.example.crossingschedule.feature.auth.presentation.model.SignUpViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(

) : ViewModel() {

    private val _signUpViewState = MutableStateFlow(SignUpViewState())
    val signUpViewState = _signUpViewState
}