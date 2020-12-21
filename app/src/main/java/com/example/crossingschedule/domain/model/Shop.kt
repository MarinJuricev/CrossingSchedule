package com.example.crossingschedule.domain.model

abstract class Shop {

    abstract val name: String
    abstract val isVisited: Boolean
}

data class NooksCranny(
    override val name: String = "NooksCranny",
    override val isVisited: Boolean = false
) : Shop()

data class AbleSisters(
    override val name: String = "AbleSisters",
    override val isVisited: Boolean = false
) : Shop()

data class Museum(
    override val name: String = "Museum",
    override val isVisited: Boolean = false
) : Shop()