package com.example.crossingschedule.feature.islandCreation.domain.usecase

import com.example.crossingschedule.core.model.buildLeft
import com.example.crossingschedule.core.model.buildRight
import com.example.crossingschedule.feature.islandCreation.domain.model.IslandCreationFailure.InvalidIslandName
import com.example.crossingschedule.feature.islandCreation.domain.repository.IslandCreationRepository
import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val ISLAND_NAME = "IslandName"
private const val NUMBER_OF_VILLAGERS = "4"
private const val FORMATTED_NUMBER_OF_VILLAGERS = 4
private const val ERROR_MESSAGE = "errorMessage"
private val HEMISPHERE = Hemisphere.NORTH

@ExperimentalCoroutinesApi
class CreateIslandTest {

    private val islandCreationValidator: IslandCreationValidator = mockk()
    private val islandCreationRepository: IslandCreationRepository = mockk()

    private lateinit var sut: CreateIsland

    @Before
    fun setUp() {
        sut = CreateIsland(
            islandCreationValidator,
            islandCreationRepository,
        )
    }

    @Test
    fun `invoke should early return Left when validator returns Left`() = runBlockingTest {
        val validationFailure = InvalidIslandName(ERROR_MESSAGE).buildLeft()

        coEvery {
            islandCreationValidator.validate(ISLAND_NAME, HEMISPHERE, NUMBER_OF_VILLAGERS)
        } coAnswers { validationFailure }

        val actualResult = sut(ISLAND_NAME, HEMISPHERE, NUMBER_OF_VILLAGERS)

        assertThat(actualResult).isEqualTo(validationFailure)
    }

    @Test
    fun `invoke should return result from repository when the validator returns Right`() =
        runBlockingTest {
            val repositoryResult = Unit.buildRight()

            coEvery {
                islandCreationValidator.validate(ISLAND_NAME, HEMISPHERE, NUMBER_OF_VILLAGERS)
            } coAnswers { Unit.buildRight() }
            coEvery {
                islandCreationRepository.createIsland(
                    ISLAND_NAME,
                    HEMISPHERE,
                    FORMATTED_NUMBER_OF_VILLAGERS
                )
            } coAnswers { repositoryResult }

            val actualResult = sut(ISLAND_NAME, HEMISPHERE, NUMBER_OF_VILLAGERS)

            assertThat(actualResult).isEqualTo(repositoryResult)
        }
}