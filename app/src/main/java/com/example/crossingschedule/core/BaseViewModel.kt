package com.example.crossingschedule.core

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<E> : ViewModel() {
    abstract fun onEvent(event: E)
}