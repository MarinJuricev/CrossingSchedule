package com.example.crossingschedule.feature.islandSelection.data.repository

import com.example.crossingschedule.R
import com.example.crossingschedule.core.ext.safeApiCall
import com.example.crossingschedule.core.model.CrossingResponse
import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.Either.Left
import com.example.crossingschedule.core.model.Either.Right
import com.example.crossingschedule.core.model.buildLeft
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.core.util.StringProvider
import com.example.crossingschedule.feature.islandSelection.data.model.IslandResponse
import com.example.crossingschedule.feature.islandSelection.data.service.IslandSelectionApiService
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandInfo
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandSelectionFailure
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandSelectionFailure.*
import com.example.crossingschedule.feature.islandSelection.domain.repository.IslandSelectionRepository
import javax.inject.Inject

class IslandSelectionRepositoryImpl @Inject constructor(
    private val islandSelectionApiService: IslandSelectionApiService,
    private val islandResponseToEitherMapper:
    Mapper<Either<IslandSelectionFailure, List<IslandInfo>>, CrossingResponse<IslandResponse>>,
    private val stringProvider: StringProvider,
) : IslandSelectionRepository {

    override suspend fun getIslands(): Either<IslandSelectionFailure, List<IslandInfo>> =
        when (val result = safeApiCall(
            stringProvider.getString(R.string.generic_error_message)
        ) {
            islandSelectionApiService.getIslands()
        }) {
            is Right -> islandResponseToEitherMapper.map(result.value)
            is Left -> RemoteFailure(result.error).buildLeft()
        }
}