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
private const val SECOND_VILLAGER_NAME = "Ava"

@ExperimentalCoroutinesApi
class VillagerInteractionGiftClickedTest {

    private val activitiesRepository: ActivitiesRepository = mockk()

    private lateinit var sut: VillagerInteractionGiftClicked

    @Before
    fun setUp() {
        sut = VillagerInteractionGiftClicked(
            activitiesRepository
        )
    }

    @Test
    fun `villagerInteractionGiftClicked should trigger repository updateVillagerInteractions with a updated list where the receivedGift is inverted`() =
        runBlockingTest {
            val villagerInteractionToChange = VillagerInteraction(FIRST_VILLAGER_NAME)
            val secondVillager = VillagerInteraction(SECOND_VILLAGER_NAME)
            val repositoryResult = Either.Right(Unit)

            val currentList = listOf(villagerInteractionToChange, secondVillager)
            val updatedList =
                listOf(villagerInteractionToChange.copy(receivedGift = true), secondVillager)

            coEvery {
                activitiesRepository.updateVillagerInteractions(updatedList, "")
            } coAnswers {
                repositoryResult
            }

            val actualResult = sut(
                "",
                villagerInteractionToChange,

                currentList
            )

            assert(actualResult == repositoryResult)
            coEvery { activitiesRepository.updateVillagerInteractions(updatedList, "") }
        }
}