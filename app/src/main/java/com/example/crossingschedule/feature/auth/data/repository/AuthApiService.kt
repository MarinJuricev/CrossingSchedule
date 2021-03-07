package com.example.crossingschedule.feature.auth.data.repository

import com.example.crossingschedule.feature.auth.data.model.AuthenticateUserResponse
import retrofit2.http.GET

interface AuthApiService {

    @GET("authenticate")
    suspend fun authenticateUser(): AuthenticateUserResponse
}