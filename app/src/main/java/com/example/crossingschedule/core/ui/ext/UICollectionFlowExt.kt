package com.example.crossingschedule.core.ui.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> Flow<T>.collectWithLifeCycle(
    initialValue: T,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): State<T> {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifeCycleAwareFlow = flowWithLifecycle(
        lifecycle = lifecycleOwner.lifecycle,
        minActiveState = minActiveState,
    )

    return lifeCycleAwareFlow.collectAsState(initial = initialValue)
}