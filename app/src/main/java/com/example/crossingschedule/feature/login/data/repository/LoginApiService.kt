package com.example.crossingschedule.feature.login.data.repository

import retrofit2.http.GET

interface LoginApiService {

    @GET("authenticate")
    suspend fun authenticateUser(): String //TODO: Actually return something meaningful from BE
}