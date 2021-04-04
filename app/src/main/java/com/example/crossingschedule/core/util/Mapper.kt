package com.example.crossingschedule.core.util

interface Mapper<R, O> {
    suspend fun map(origin: O): R
}