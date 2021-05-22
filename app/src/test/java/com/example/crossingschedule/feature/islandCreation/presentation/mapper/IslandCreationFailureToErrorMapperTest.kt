package com.example.crossingschedule.feature.islandCreation.presentation.mapper

import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.islandCreation.domain.model.IslandCreationFailure
import com.example.crossingschedule.feature.islandCreation.presentation.model.IslandCreationError
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val ERROR_MESSAGE = "errorMessage"

@ExperimentalCoroutinesApi
class IslandCreationFailureToErrorMapperTest {

    private lateinit var sut: Mapper<IslandCreationError, IslandCreationFailure>

    @Before
    fun setUp() {
        sut = IslandCreationFailureToErrorMapper()
    }

    @Test
    fun `map should return NameError when InvalidIslandName is provided`() = runBlockingTest {
        val origin = IslandCreationFailure.InvalidIslandName(ERROR_MESSAGE)

        val actualResult = sut.map(origin)
        val expectedResult = IslandCreationError.NameError(ERROR_MESSAGE)

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `map should return HemisphereError when InvalidHemisphere is provided`() = runBlockingTest {
        val origin = IslandCreationFailure.InvalidHemisphere(ERROR_MESSAGE)

        val actualResult = sut.map(origin)
        val expectedResult = IslandCreationError.HemisphereError(ERROR_MESSAGE)

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `map should return NumberOfVillagersError when InvalidNumberOfVillagers is provided`() =
        runBlockingTest {
            val origin = IslandCreationFailure.InvalidNumberOfVillagers(ERROR_MESSAGE)

            val actualResult = sut.map(origin)
            val expectedResult = IslandCreationError.NumberOfVillagersError(ERROR_MESSAGE)

            assertThat(actualResult).isEqualTo(expectedResult)
        }

    @Test
    fun `map should return GeneralError when RemoteFailure is provided`() = runBlockingTest {
        val origin = IslandCreationFailure.RemoteFailure(ERROR_MESSAGE)

        val actualResult = sut.map(origin)
        val expectedResult = IslandCreationError.GeneralError(ERROR_MESSAGE)

        assertThat(actualResult).isEqualTo(expectedResult)
    }


}