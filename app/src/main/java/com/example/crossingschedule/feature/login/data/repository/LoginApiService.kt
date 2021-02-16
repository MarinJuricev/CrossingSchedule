package com.example.crossingschedule.feature.login.data.repository

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginApiService {

    @GET("authenticated")
    suspend fun authenticateUser(@Header("Authorization: Bearer") loginToken: String): Response<String> //TODO: Actually return something meaningful from BE
}