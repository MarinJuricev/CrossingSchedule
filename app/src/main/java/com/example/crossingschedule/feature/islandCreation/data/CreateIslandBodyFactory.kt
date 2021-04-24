package com.example.crossingschedule.feature.islandCreation.data

import com.example.crossingschedule.feature.islandCreation.data.model.CreateIslandBody
import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere
import javax.inject.Inject

class CreateIslandBodyFactory @Inject constructor() {

    fun create(
        islandName: String,
        hemisphere: Hemisphere,
        numberOfVillagers: Int,
    ) = CreateIslandBody(
        islandName = islandName,
        hemisphere = hemisphere,
        numberOfVillagers = numberOfVillagers,
    )
}