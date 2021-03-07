package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.feature.schedule.domain.model.VillagerInteraction
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

private const val FIRST_VILLAGER_NAME = "Sterling"

@ExperimentalCoroutinesApi
class DeleteVillagerInteractionTest {

    private val activitiesRepository: ActivitiesRepository = mockk()

    lateinit var sut: DeleteVillagerInteraction

    @Before
    fun setUp() {
        sut = DeleteVillagerInteraction(
            activitiesRepository
        )
    }

    @Test
    fun `deleteVillagerInteraction should trigger repository updateVillagerInteractions with newly generatedList when the provided list is empty`() =
        runBlockingTest {
            val generatedList = listOf(VillagerInteraction(FIRST_VILLAGER_NAME))
            coEvery {
                activitiesRepository.updateVillagerInteractions(
                    generatedList,
                    ""
                )
            } coAnswers {
                Either.Right(Unit)
            }

            val actualResult = sut(listOf(), "", VillagerInteraction(FIRST_VILLAGER_NAME))
            val expectedResult = Either.Right(Unit)

            assert(actualResult == expectedResult)
        }

    @Test
    fun `deleteVillagerInteraction should trigger repository updateVillagerInteractions with newly generatedList with a empty list when the provided list contains only one item`() =
        runBlockingTest {
            val generatedList = listOf<VillagerInteraction>()
            coEvery {
                activitiesRepository.updateVillagerInteractions(
                    generatedList,
                    ""
                )
            } coAnswers {
                Either.Right(Unit)
            }

            val actualResult = sut(
                listOf(VillagerInteraction(FIRST_VILLAGER_NAME)),
                currentDate = "",
                VillagerInteraction(FIRST_VILLAGER_NAME)
            )
            val expectedResult = Either.Right(Unit)

            assert(actualResult == expectedResult)
        }
}