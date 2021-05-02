package com.example.crossingschedule.feature.islandSelection.data.mapper

import com.example.crossingschedule.R
import com.example.crossingschedule.core.model.*
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.core.util.StringProvider
import com.example.crossingschedule.feature.islandSelection.data.model.IslandResponse
import com.example.crossingschedule.feature.islandSelection.data.model.RemoteIslandInfo
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandInfo
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandSelectionFailure
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandSelectionFailure.RemoteFailure
import javax.inject.Inject

class IslandResponseToEitherMapper @Inject constructor(
    private val remoteIslandInfoToIslandInfoMapper: Mapper<List<IslandInfo>, List<RemoteIslandInfo>>,
    private val stringProvider: StringProvider,
) :
    Mapper<Either<IslandSelectionFailure, List<IslandInfo>>, CrossingResponse<IslandResponse>> {

    override suspend fun map(origin: CrossingResponse<IslandResponse>): Either<IslandSelectionFailure, List<IslandInfo>> {
        return if (origin.status == CrossingStatus.Success && origin.data != null) {
            remoteIslandInfoToIslandInfoMapper.map(origin.data.availableIslands).buildRight()
        } else {
            RemoteFailure(
                origin.message ?: stringProvider.getString(R.string.generic_error_message)
            ).buildLeft()
        }
    }
}