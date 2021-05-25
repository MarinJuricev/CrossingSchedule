package com.example.crossingschedule.feature.islandSelection.data.mapper

import com.example.crossingschedule.core.util.DateHandler
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.islandSelection.data.model.RemoteIslandInfo
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandInfo
import javax.inject.Inject

class RemoteIslandInfoToIslandInfoMapper @Inject constructor(
    private val dateHandler: DateHandler,
) : Mapper<List<IslandInfo>, List<RemoteIslandInfo>> {

    override suspend fun map(origin: List<RemoteIslandInfo>): List<IslandInfo> =
        origin.map { remoteIslandInfo ->
            with(remoteIslandInfo) {
                IslandInfo(
                    id = id,
                    name = name,
                    hemisphere = hemisphere,
                    numberOfVillagers = numberOfVillagers,
                    lastVisited = dateHandler.fromTimeStampToCrossingDay(lastVisited)
                )
            }
        }
}