package com.example.crossingschedule.feature.auth.domain

//If we were to refactor this in a separate module we'd mark this as internal
const val EMAIL_REGEX =
    "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
const val MINIMAL_PASSWORD_LENGTH = 8