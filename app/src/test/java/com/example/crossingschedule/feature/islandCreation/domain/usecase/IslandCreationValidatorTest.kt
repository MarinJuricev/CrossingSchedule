package com.example.crossingschedule.feature.islandCreation.domain.usecase

import com.example.crossingschedule.core.model.buildLeft
import com.example.crossingschedule.core.model.buildRight
import com.example.crossingschedule.feature.islandCreation.domain.model.IslandCreationFailure.*
import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class IslandCreationValidatorTest {

    private lateinit var sut: IslandCreationValidator

    @Before
    fun setUp() {
        sut = IslandCreationValidator()
    }

    @Test
    fun `validate should return InvalidIslandName when islandName is blank`() {
        val actualResult = sut.validate("", Hemisphere.NORTH, "5")
        val expectedResult = InvalidIslandName("Island can't be empty").buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `validate should return InvalidHemisphere when hemisphere is null`() {
        val actualResult = sut.validate("islandName", null, "5")
        val expectedResult = InvalidHemisphere("Hemisphere can't be empty").buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `validate should return InvalidNumberOfVillagers when numberOfVillagers is blank`() {
        val actualResult = sut.validate("islandName", Hemisphere.NORTH, "")
        val expectedResult =
            InvalidNumberOfVillagers("Number of villagers must be a number and must be specified").buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `validate should return InvalidNumberOfVillagers when numberOfVillagers is not a number`() {
        val actualResult = sut.validate("islandName", Hemisphere.NORTH, "invalidEntry")
        val expectedResult =
            InvalidNumberOfVillagers("Number of villagers must be a number and must be specified").buildLeft()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `validate should return Unit when all of the validation passes`() {
        val actualResult = sut.validate("islandName", Hemisphere.NORTH, "9")
        val expectedResult = Unit.buildRight()


        assertThat(actualResult).isEqualTo(expectedResult)
    }
}
