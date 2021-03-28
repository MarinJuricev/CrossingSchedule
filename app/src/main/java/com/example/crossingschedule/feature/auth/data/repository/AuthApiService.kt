package com.example.crossingschedule.feature.auth.data.repository

import com.example.crossingschedule.feature.auth.data.model.CreateAccountBody
import com.example.crossingschedule.feature.auth.data.model.LoginUserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {

    @GET("login")
    suspend fun loginUser(): LoginUserResponse

    @POST("signup")
    suspend fun createAccount(@Body createAccountBody: CreateAccountBody): LoginUserResponse
}