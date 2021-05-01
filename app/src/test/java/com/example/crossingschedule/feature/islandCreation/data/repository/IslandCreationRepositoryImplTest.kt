package com.example.crossingschedule.feature.islandCreation.data.repository

import com.example.crossingschedule.core.model.CrossingResponse
import com.example.crossingschedule.core.model.CrossingStatus
import com.example.crossingschedule.core.model.buildLeft
import com.example.crossingschedule.core.model.buildRight
import com.example.crossingschedule.feature.islandCreation.data.CreateIslandBodyFactory
import com.example.crossingschedule.feature.islandCreation.data.model.CreateIslandBody
import com.example.crossingschedule.feature.islandCreation.data.service.IslandCreationApiService
import com.example.crossingschedule.feature.islandCreation.domain.model.IslandCreationFailure.RemoteFailure
import com.example.crossingschedule.feature.islandCreation.domain.repository.IslandCreationRepository
import com.example.crossingschedule.feature.islandSelection.domain.model.Hemisphere
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val ISLAND_NAME = "islandName"
private const val ERROR_MESSAGE = "errorMessage"
private const val NUMBER_OF_VILLAGERS = 12
private val HEMISPHERE = Hemisphere.NORTH

@ExperimentalCoroutinesApi
class IslandCreationRepositoryImplTest {

    private val islandCreationApiService: IslandCreationApiService = mockk()
    private val createIslandBodyFactory: CreateIslandBodyFactory = mockk()

    lateinit var sut: IslandCreationRepository

    @Before
    fun setUp() {
        sut = IslandCreationRepositoryImpl(
            islandCreationApiService,
            createIslandBodyFactory
        )
    }

    @Test
    fun `createIsland should return RemoteFailure when islandCreationApiService createIsland returns Left`() =
        runBlockingTest {
            val requestBody = CreateIslandBody(
                islandName = ISLAND_NAME,
                hemisphere = HEMISPHERE,
                numberOfVillagers = NUMBER_OF_VILLAGERS,
            )

            coEvery {
                createIslandBodyFactory.create(
                    islandName = ISLAND_NAME,
                    hemisphere = HEMISPHERE,
                    numberOfVillagers = NUMBER_OF_VILLAGERS
                )
            } coAnswers { requestBody }
            coEvery {
                islandCreationApiService.createIsland(
                    requestBody
                )
            } coAnswers { throw Exception(ERROR_MESSAGE) }

            val actualResult = sut.createIsland(ISLAND_NAME, HEMISPHERE, NUMBER_OF_VILLAGERS)

            assertThat(actualResult).isEqualTo(RemoteFailure(ERROR_MESSAGE).buildLeft())
        }

    @Test
    fun `createIsland should return Unit when islandCreationApiService createIsland returns Right`() =
        runBlockingTest {
            val requestBody = CreateIslandBody(
                islandName = ISLAND_NAME,
                hemisphere = HEMISPHERE,
                numberOfVillagers = NUMBER_OF_VILLAGERS,
            )
            val successResponse = CrossingResponse(
                status = CrossingStatus.Success,
                data = "",
            )

            coEvery {
                createIslandBodyFactory.create(
                    islandName = ISLAND_NAME,
                    hemisphere = HEMISPHERE,
                    numberOfVillagers = NUMBER_OF_VILLAGERS
                )
            } coAnswers { requestBody }
            coEvery {
                islandCreationApiService.createIsland(
                    requestBody
                )
            } coAnswers {
                successResponse
            }

            val actualResult = sut.createIsland(ISLAND_NAME, HEMISPHERE, NUMBER_OF_VILLAGERS)

            assertThat(actualResult).isEqualTo(Unit.buildRight())
        }
}