package com.example.crossingschedule.feature.islandSelection.data.service

import com.example.crossingschedule.core.model.CrossingResponse
import com.example.crossingschedule.feature.islandSelection.data.model.IslandResponse
import retrofit2.http.GET

interface IslandSelectionApiService {

    @GET("islands")
    suspend fun getIslands(): CrossingResponse<IslandResponse>
}