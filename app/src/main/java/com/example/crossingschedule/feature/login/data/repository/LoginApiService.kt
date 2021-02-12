package com.example.crossingschedule.feature.login.data.repository

import retrofit2.Response
import retrofit2.http.POST

interface LoginApiService {

    @POST("authenticated")
    suspend fun authenticateUser(loginToken: String): Response<String> //TODO: Actually return something meaningful from BE
}