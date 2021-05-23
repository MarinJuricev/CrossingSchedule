package com.example.crossingschedule.feature.islandCreation.presentation.viewmodel

import app.cash.turbine.test
import com.example.crossingschedule.core.model.buildLeft
import com.example.crossingschedule.core.model.buildRight
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.islandCreation.domain.model.IslandCreationFailure
import com.example.crossingschedule.feature.islandCreation.domain.usecase.CreateIsland
import com.example.crossingschedule.feature.islandCreation.presentation.model.IslandCreationError
import com.example.crossingschedule.feature.islandCreation.presentation.model.IslandCreationEvent.*
import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere
import com.example.crossingschedule.testUtilities.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

private const val ISLAND_NAME = "islandName"
private const val NUMBER_OF_VILLAGERS = "12"
private const val ERROR_MESSAGE = "errorMessage"
private val newHemisphere = Hemisphere.NORTH

@ExperimentalTime
@ExperimentalCoroutinesApi
class IslandCreationViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val createIsland: CreateIsland = mockk()
    private val islandCreationFailureToErrorMapper: Mapper<IslandCreationError, IslandCreationFailure> =
        mockk()

    private lateinit var sut: IslandCreationViewModel

    @Before
    fun setUp() {
        sut = IslandCreationViewModel(
            createIsland,
            islandCreationFailureToErrorMapper,
        )
    }

    @Test
    fun `onEvent OnIslandNameChange should update viewStates name`() = runBlockingTest {
        sut.onEvent(OnIslandNameChange(ISLAND_NAME))

        sut.islandCreationViewState.test {
            assertThat(expectItem().name).isEqualTo(ISLAND_NAME)
        }
    }

    @Test
    fun `onEvent OnHemisphereChanged should update viewStates hemisphere`() = runBlockingTest {
        sut.onEvent(OnHemisphereChanged(newHemisphere))

        sut.islandCreationViewState.test {
            assertThat(expectItem().hemisphere).isEqualTo(newHemisphere)
        }
    }

    @Test
    fun `onEvent OnNumberOfVillagersChanged should update viewStates numberOfVillagers`() =
        runBlockingTest {
            sut.onEvent(OnNumberOfVillagersChanged(NUMBER_OF_VILLAGERS))

            sut.islandCreationViewState.test {
                assertThat(expectItem().numberOfVillagers).isEqualTo(NUMBER_OF_VILLAGERS)
            }
        }

    @Test
    fun `onEvent OnCreateClicked should update viewStates islandCreationError when CreateIsland returns Left`() =
        runBlockingTest {
            val failure = IslandCreationFailure.RemoteFailure(ERROR_MESSAGE)
            val error = IslandCreationError.GeneralError(ERROR_MESSAGE)
            coEvery {
                createIsland(any(), any(), any())
            } coAnswers { failure.buildLeft() }
            coEvery {
                islandCreationFailureToErrorMapper.map(failure)
            } coAnswers { error }
            sut.onEvent(OnCreateClicked)

            sut.islandCreationViewState.test {
                val expectedItem = expectItem()

                assertThat(expectedItem.islandCreationError).isEqualTo(error)
                assertThat(expectedItem.isLoading).isEqualTo(false)
            }
        }

    @Test
    fun `onEvent OnCreateClicked should update viewStates islandCreationSuccess when CreateIsland returns Right`() =
        runBlockingTest {
            val useCaseResult = Unit
            coEvery {
                createIsland(any(), any(), any())
            } coAnswers { useCaseResult.buildRight() }
            sut.onEvent(OnCreateClicked)

            sut.islandCreationViewState.test {
                val expectedItem = expectItem()

                assertThat(expectedItem.islandCreationSuccess).isEqualTo(useCaseResult)
                assertThat(expectedItem.isLoading).isEqualTo(false)
            }
        }
}