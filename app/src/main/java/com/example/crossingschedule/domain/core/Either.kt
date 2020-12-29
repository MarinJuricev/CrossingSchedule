package com.example.crossingschedule.domain.core

sealed class Either<out E, out V> {
    data class Success<out V>(val value: V) : Either<Nothing, V>()
    data class Error<out E>(val error: E) : Either<E, Nothing>()
}