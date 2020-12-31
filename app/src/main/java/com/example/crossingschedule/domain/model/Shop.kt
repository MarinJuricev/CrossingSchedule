package com.example.crossingschedule.domain.model

data class Shop(
    val name: String = "",
    @field:JvmField //TODO: Needed for firestore, because the boolean has a it prefix, maybe separate this into a data module ???
    val isVisited: Boolean = false
)