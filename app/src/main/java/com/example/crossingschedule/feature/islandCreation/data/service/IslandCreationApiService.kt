package com.example.crossingschedule.feature.islandCreation.data.service

import com.example.crossingschedule.core.model.CrossingResponse
import com.example.crossingschedule.feature.islandCreation.data.model.CreateIslandBody
import retrofit2.http.Body
import retrofit2.http.POST

interface IslandCreationApiService {

    @POST("island")
    suspend fun createIsland(@Body createIslandBody: CreateIslandBody): CrossingResponse<String>
}