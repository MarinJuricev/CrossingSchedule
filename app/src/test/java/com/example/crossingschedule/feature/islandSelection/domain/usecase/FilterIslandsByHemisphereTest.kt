package com.example.crossingschedule.feature.islandSelection.domain.usecase

import com.example.crossingschedule.core.model.CrossingDay
import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere
import com.example.crossingschedule.feature.islandSelection.domain.model.IslandInfo
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class FilterIslandsByHemisphereTest {

    private lateinit var sut: FilterIslandsByHemisphere

    @Before
    fun setUp() {
        sut = FilterIslandsByHemisphere()
    }

    @Test
    fun `invoke should return only island in the South hemisphere when Hemisphere NORTH is provided`() {
        val currentIslandInfo = listOf(
            IslandInfo("", "NORTH", Hemisphere.NORTH, -1, CrossingDay(-1, -1, -1)),
            IslandInfo("", "SOUTH", Hemisphere.SOUTH, -1, CrossingDay(-1, -1, -1))
        )
        val currentFilterState = Hemisphere.NORTH

        val actualResult = sut(currentIslandInfo, currentFilterState)
        val expectedResult =
            listOf(
                IslandInfo("", "SOUTH", Hemisphere.SOUTH, -1, CrossingDay(-1, -1, -1))
            )

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `invoke should return only island in the North hemisphere when Hemisphere SOUTH is provided`() {
        val currentIslandInfo = listOf(
            IslandInfo("", "NORTH", Hemisphere.NORTH, -1, CrossingDay(-1, -1, -1)),
            IslandInfo("", "SOUTH", Hemisphere.SOUTH, -1, CrossingDay(-1, -1, -1))
        )
        val currentFilterState = Hemisphere.SOUTH

        val actualResult = sut(currentIslandInfo, currentFilterState)
        val expectedResult =
            listOf(
                IslandInfo("", "NORTH", Hemisphere.NORTH, -1, CrossingDay(-1, -1, -1))
            )

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `invoke should return the provided islandInfo when null is provided as the newHemisphere`() {
        val currentIslandInfo = listOf(
            IslandInfo("", "NORTH", Hemisphere.NORTH, -1, CrossingDay(-1, -1, -1)),
            IslandInfo("", "SOUTH", Hemisphere.SOUTH, -1, CrossingDay(-1, -1, -1))
        )
        val currentFilterState = null

        val actualResult = sut(currentIslandInfo, currentFilterState)
        val expectedResult =
            listOf(
                IslandInfo("", "NORTH", Hemisphere.NORTH, -1, CrossingDay(-1, -1, -1)),
                IslandInfo("", "SOUTH", Hemisphere.SOUTH, -1, CrossingDay(-1, -1, -1))
            )

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}