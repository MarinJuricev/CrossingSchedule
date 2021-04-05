package com.example.crossingschedule.feature.islands.domain.usecase

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.buildRight
import com.example.crossingschedule.core.util.DateHandler
import com.example.crossingschedule.feature.islands.domain.model.Hemisphere
import com.example.crossingschedule.feature.islands.domain.model.IslandInfo
import com.example.crossingschedule.feature.islands.domain.model.IslandsFailure
import java.util.*
import javax.inject.Inject

class GetIslands @Inject constructor(
    private val dateHandler: DateHandler // TODO: 4/4/21 Delete later, only used for generating mock data for now 
) {

    suspend operator fun invoke(): Either<IslandsFailure, List<IslandInfo>> {
        val currentCrossingDay = dateHandler.provideCurrentCrossingDay()

        return listOf(
            IslandInfo(
                UUID.randomUUID().toString(),
                "firstIsland",
                Hemisphere.NORTH,
                5,
                currentCrossingDay
            ),
            IslandInfo(
                UUID.randomUUID().toString(),
                "secondIsland",
                Hemisphere.SOUTH,
                3,
                currentCrossingDay
            ),
            IslandInfo(
                UUID.randomUUID().toString(),
                "thirdIsland",
                Hemisphere.SOUTH,
                7,
                currentCrossingDay
            ),
            IslandInfo(
                UUID.randomUUID().toString(),
                "Islanddd for daayz",
                Hemisphere.NORTH,
                2,
                currentCrossingDay
            ),
        ).buildRight()
    }
}