package com.example.crossingschedule.feature.auth.data.service

import com.example.crossingschedule.core.model.CrossingResponse
import com.example.crossingschedule.feature.auth.data.model.AuthResponse
import com.example.crossingschedule.feature.auth.data.model.CreateAccountBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {

    @GET("login")
    suspend fun loginUser(): CrossingResponse<AuthResponse>

    @POST("signup")
    suspend fun createAccount(@Body createAccountBody: CreateAccountBody): CrossingResponse<AuthResponse>
}