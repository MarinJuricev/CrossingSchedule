package com.example.crossingschedule.domain.model

data class VillagerInteraction(
    val villagerName: String,
    val talkedTo: Boolean,
    val receivedGift: Boolean
)