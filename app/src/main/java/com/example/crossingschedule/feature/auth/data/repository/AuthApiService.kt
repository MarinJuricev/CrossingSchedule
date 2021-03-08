package com.example.crossingschedule.feature.auth.data.repository

import com.example.crossingschedule.feature.auth.data.model.LoginUserResponse
import retrofit2.http.GET

interface AuthApiService {

    @GET("login")
    suspend fun loginUser(): LoginUserResponse
}