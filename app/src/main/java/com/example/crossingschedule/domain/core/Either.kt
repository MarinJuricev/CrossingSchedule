package com.example.crossingschedule.domain.core

sealed class Either<out E, out V> {
    data class Right<out V>(val value: V) : Either<Nothing, V>()
    data class Left<out E>(val error: E) : Either<E, Nothing>()
}