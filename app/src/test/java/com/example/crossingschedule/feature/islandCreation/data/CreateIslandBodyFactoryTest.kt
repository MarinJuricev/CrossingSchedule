package com.example.crossingschedule.feature.islandCreation.data

import com.example.crossingschedule.feature.islandCreation.data.model.CreateIslandBody
import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

private const val ISLAND_NAME = "island"
private const val NUMBER_OF_VILLAGERS = 4
private val HEMISPHERE = Hemisphere.NORTH

class CreateIslandBodyFactoryTest {

    lateinit var sut: CreateIslandBodyFactory

    @Before
    fun setUp() {
        sut = CreateIslandBodyFactory()
    }

    @Test
    fun `create should create a valid CreateIslandBody instance when islandName, hemisphere, numberOfVillagers is provided`() {
        val actualResult = sut.create(ISLAND_NAME, HEMISPHERE, NUMBER_OF_VILLAGERS)
        val expectedResult = CreateIslandBody(
            islandName = ISLAND_NAME,
            hemisphere = HEMISPHERE,
            numberOfVillagers = NUMBER_OF_VILLAGERS
        )

        assertThat(actualResult).isEqualTo(expectedResult)
    }
}