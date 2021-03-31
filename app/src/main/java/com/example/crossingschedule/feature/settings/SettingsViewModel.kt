package com.example.crossingschedule.feature.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

// TODO: 01. 04. 2021. Only used for a presentation to display the new flow lifeCycle APIs, to lazy to create a new project, will be deleted later

class SettingsViewModel : ViewModel() {

    fun observeConstantStream() =
        flow {
            while (true) {
                delay(1500)
                val valueToEmit = Random.nextInt()
                emit(valueToEmit)
                Log.d("SettingsViewModel", "SettingsViewModel: $valueToEmit")

            }
        }
}