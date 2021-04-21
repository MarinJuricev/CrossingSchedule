package com.example.crossingschedule.feature.islandCreation.data.repository

import com.example.crossingschedule.core.ext.safeApiCall
import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.buildLeft
import com.example.crossingschedule.core.model.buildRight
import com.example.crossingschedule.feature.islandCreation.data.CreateIslandBodyFactory
import com.example.crossingschedule.feature.islandCreation.data.service.IslandCreationApiService
import com.example.crossingschedule.feature.islandCreation.domain.model.IslandCreationFailure
import com.example.crossingschedule.feature.islandCreation.domain.repository.IslandCreationRepository
import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere
import javax.inject.Inject

class IslandCreationRepositoryImpl @Inject constructor(
    private val islandCreationApiService: IslandCreationApiService,
    private val createIslandBodyFactory: CreateIslandBodyFactory,
) : IslandCreationRepository {

    override suspend fun createIsland(
        islandName: String,
        hemisphere: Hemisphere,
        numberOfVillagers: Int
    ): Either<IslandCreationFailure, Unit> {
        val requestBody = createIslandBodyFactory.create(
            islandName = islandName,
            hemisphere = hemisphere,
            numberOfVillagers = numberOfVillagers,
        )

        return when (val result = safeApiCall {
            islandCreationApiService.createIsland(requestBody)
        }) {
            is Either.Left -> IslandCreationFailure.RemoteFailure(result.error).buildLeft()
            is Either.Right -> Unit.buildRight()
        }
    }
}