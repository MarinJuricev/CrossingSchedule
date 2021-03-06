package com.example.crossingschedule.feature.islandCreation.data.repository

import com.example.crossingschedule.R
import com.example.crossingschedule.core.ext.safeApiCall
import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.Either.Left
import com.example.crossingschedule.core.model.Either.Right
import com.example.crossingschedule.core.model.buildLeft
import com.example.crossingschedule.core.model.buildRight
import com.example.crossingschedule.core.util.StringProvider
import com.example.crossingschedule.feature.islandCreation.data.CreateIslandBodyFactory
import com.example.crossingschedule.feature.islandCreation.data.service.IslandCreationApiService
import com.example.crossingschedule.feature.islandCreation.domain.model.IslandCreationFailure
import com.example.crossingschedule.feature.islandCreation.domain.model.IslandCreationFailure.RemoteFailure
import com.example.crossingschedule.feature.islandCreation.domain.repository.IslandCreationRepository
import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere
import javax.inject.Inject

class IslandCreationRepositoryImpl @Inject constructor(
    private val islandCreationApiService: IslandCreationApiService,
    private val createIslandBodyFactory: CreateIslandBodyFactory,
    private val stringProvider: StringProvider,
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

        return when (val result = safeApiCall(
            stringProvider.getString(R.string.generic_error_message),
        ) {
            islandCreationApiService.createIsland(requestBody)
        }) {
            is Left -> RemoteFailure(result.error).buildLeft()
            is Right -> Unit.buildRight()
        }
    }
}