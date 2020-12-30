package com.example.crossingschedule.domain.core

interface Mapper<R, O> {
    fun map(origin: O): R
}