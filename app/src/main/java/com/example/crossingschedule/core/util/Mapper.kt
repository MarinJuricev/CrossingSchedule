package com.example.crossingschedule.core.util

interface Mapper<R, O> {
    fun map(origin: O): R
}