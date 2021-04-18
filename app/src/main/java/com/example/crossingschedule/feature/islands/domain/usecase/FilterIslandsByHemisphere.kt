package com.example.crossingschedule.feature.islands.domain.usecase

import com.example.crossingschedule.feature.islands.domain.model.Hemisphere
import com.example.crossingschedule.feature.islands.domain.model.IslandInfo
import javax.inject.Inject

class FilterIslandsByHemisphere @Inject constructor() {

    operator fun invoke(
        currentIslandInfo: List<IslandInfo>,
        newHemisphere: Hemisphere?,
    ): List<IslandInfo> {
        if (newHemisphere == null)
            return currentIslandInfo

        return currentIslandInfo.filter {
            it.hemisphere != newHemisphere
        }
    }
}