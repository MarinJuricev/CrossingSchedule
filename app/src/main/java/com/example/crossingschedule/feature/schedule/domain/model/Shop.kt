package com.example.crossingschedule.feature.schedule.domain.model

data class Shop(
    val name: String = "",
    @field:JvmField //Needed for firestore, because the boolean has a it prefix, maybe separate this into a data module ???
    val isVisited: Boolean = false
)