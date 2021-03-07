package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.schedule.domain.model.TurnipPriceType
import com.example.crossingschedule.feature.schedule.domain.model.TurnipPrices
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import com.example.crossingschedule.feature.schedule.presentation.model.UiTurnipPrices
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before

import org.junit.Test

private const val CURRENT_AM_PRICE = "20"
private const val CURRENT_PM_PRICE = "50"
private const val UPDATED_AM_PRICE = "150"
private const val UPDATED_PM_PRICE = "100"

@ExperimentalCoroutinesApi
class UpdateTurnipPricesTest {

    private val activitiesRepository: ActivitiesRepository = mockk()
    private val uiTurnipPricesToTurnipPricesMapper: Mapper<TurnipPrices, UiTurnipPrices> = mockk()

    private lateinit var sut: UpdateTurnipPrices


    @Before
    fun setUp() {
        sut = UpdateTurnipPrices(
            activitiesRepository,
            uiTurnipPricesToTurnipPricesMapper
        )
    }

    @Test
    fun `updateTurnipPrices should update TurnipPrices when the provided currentPrices is AM`() =
        runBlockingTest {
            val turnipPriceType = TurnipPriceType.AM
            val currentTurnipPrices = UiTurnipPrices(
                amPrice = CURRENT_AM_PRICE
            )
            val expectedTurnipPrices = TurnipPrices(
                amPrice = 150,
                pmPrice = 0
            )
            val repositoryResult = Either.Right(Unit)


            coEvery {
                activitiesRepository.updateTurnipPrices(expectedTurnipPrices, "")
            } coAnswers {
                repositoryResult
            }
            coEvery {
                uiTurnipPricesToTurnipPricesMapper.map(currentTurnipPrices)
            } coAnswers {
                expectedTurnipPrices
            }

            val actualResult = sut(currentTurnipPrices, "", turnipPriceType, UPDATED_AM_PRICE)

            assert(actualResult == repositoryResult)
            coVerify { activitiesRepository.updateTurnipPrices(expectedTurnipPrices, "") }
        }

    @Test
    fun `updateTurnipPrices should update TurnipPrices when the provided currentPrices is PM`() =
        runBlockingTest {
            val turnipPriceType = TurnipPriceType.PM
            val currentTurnipPrices = UiTurnipPrices(
                pmPrice = CURRENT_PM_PRICE
            )
            val expectedTurnipPrices = TurnipPrices(
                amPrice = 0,
                pmPrice = 100
            )
            val repositoryResult = Either.Right(Unit)


            coEvery {
                activitiesRepository.updateTurnipPrices(expectedTurnipPrices, "")
            } coAnswers {
                repositoryResult
            }
            coEvery {
                uiTurnipPricesToTurnipPricesMapper.map(currentTurnipPrices)
            } coAnswers {
                expectedTurnipPrices
            }

            val actualResult = sut(currentTurnipPrices, "", turnipPriceType, UPDATED_PM_PRICE)

            assert(actualResult == repositoryResult)
            coVerify { activitiesRepository.updateTurnipPrices(expectedTurnipPrices, "") }
        }


}