package com.example.crossingschedule.feature.islands.domain.usecase

import com.example.crossingschedule.core.model.CrossingDay
import com.example.crossingschedule.feature.islands.domain.model.Hemisphere
import com.example.crossingschedule.feature.islands.domain.model.IslandInfo
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

class FilterIslandsByHemisphereTest {

    private lateinit var sut: FilterIslandsByHemisphere

    @Before
    fun setUp() {
        sut = FilterIslandsByHemisphere()
    }

    @Test
    fun `invoke should return`() {
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

        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }
}