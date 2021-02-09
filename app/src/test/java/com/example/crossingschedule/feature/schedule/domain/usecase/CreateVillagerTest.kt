package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.feature.schedule.domain.model.VillagerInteraction
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val NEW_VILLAGER_NAME = "NEW_VILLAGER_NAME"
private const val NEW_VILLAGER_NAME_2 = "NEW_VILLAGER_NAME_2"

@ExperimentalCoroutinesApi
class CreateVillagerTest {

    private val activitiesRepository: ActivitiesRepository = mockk()

    lateinit var sut: CreateVillager

    @Before
    fun setUp() {
        sut = CreateVillager(activitiesRepository)
    }

    @Test
    fun `createVillager should trigger repository updateVillagerInteractions with newly generatedList when the provided list is empty`() =
        runBlockingTest {
            val generatedList = listOf(VillagerInteraction(NEW_VILLAGER_NAME))
            coEvery {
                activitiesRepository.updateVillagerInteractions(
                    generatedList,
                    ""
                )
            } coAnswers {
                Either.Right(Unit)
            }

            val actualResult = sut(listOf(), "", NEW_VILLAGER_NAME)
            val expectedResult = Either.Right(Unit)

            assert(actualResult == expectedResult)
        }

    @Test
    fun `createVillager should trigger repository updateVillagerInteractions with newly updatedList when the provided list is not empty`() =
        runBlockingTest {
            val currentList = listOf(VillagerInteraction(NEW_VILLAGER_NAME))
            val generatedList = listOf(
                VillagerInteraction(NEW_VILLAGER_NAME),
                VillagerInteraction(NEW_VILLAGER_NAME_2)
            )
            coEvery {
                activitiesRepository.updateVillagerInteractions(
                    generatedList,
                    ""
                )
            } coAnswers {
                Either.Right(Unit)
            }

            val actualResult = sut(currentList, "", NEW_VILLAGER_NAME_2)
            val expectedResult = Either.Right(Unit)

            assert(actualResult == expectedResult)
        }
}