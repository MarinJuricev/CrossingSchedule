package com.example.crossingschedule.core.util

import com.example.crossingschedule.BuildConfig
import javax.inject.Inject

class BaseUrlProvider @Inject constructor() {

    operator fun invoke() =
        if (BuildConfig.DEBUG) {
            //Emulator Debug Endpoint
            "http://10.0.2.2:8080/"
        } else {
            "https://crossing-schedule.herokuapp.com/"
        }
}