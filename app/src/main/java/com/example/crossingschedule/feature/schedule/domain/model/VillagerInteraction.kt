package com.example.crossingschedule.feature.schedule.domain.model

data class VillagerInteraction(
    val villagerName: String = "",
    val talkedTo: Boolean = false,
    val receivedGift: Boolean = false
)