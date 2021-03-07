package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.R
import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.schedule.domain.model.Shop
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import com.example.crossingschedule.feature.schedule.presentation.model.UiShop
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ShopItemDoneClickedTest {

    private val activitiesRepository: ActivitiesRepository = mockk()
    private val uiShopListToShopListMapper: Mapper<List<Shop>, List<UiShop>> = mockk()

    private lateinit var sut: ShopItemDoneClicked

    @Before
    fun setUp() {
        sut = ShopItemDoneClicked(
            activitiesRepository,
            uiShopListToShopListMapper
        )
    }

    @Test
    fun `shopItemDoneClicked should trigger repository updateShopItems with newly generatedList when the provided list is empty`() =
        runBlockingTest {
            val shop = Shop()
            val uiShop = UiShop(isVisited = true)

            val generatedList = listOf(shop)
            coEvery { activitiesRepository.updateShopItems(generatedList, "") } coAnswers {
                Either.Right(Unit)
            }
            coEvery { uiShopListToShopListMapper.map(listOf(uiShop)) } coAnswers {
                generatedList
            }

            val actualResult = sut(listOf(), "", UiShop())
            val expectedResult = Either.Right(Unit)

            assert(actualResult == expectedResult)
        }

    @Test
    fun `shopItemDoneClicked should trigger repository updateShopItems with the updated shop item when the provided list is contains that item`() =
        runBlockingTest {
            val shop = Shop()
            val museumShop = Shop("Museum")

            val uiMuseumShop = UiShop(R.drawable.museum_shop_icon)
            val uiShop = UiShop(isVisited = true)

            val generatedList = listOf(museumShop, shop)

            coEvery { activitiesRepository.updateShopItems(generatedList, "") } coAnswers {
                Either.Right(Unit)
            }
            coEvery { uiShopListToShopListMapper.map(listOf(uiMuseumShop, uiShop)) } coAnswers {
                generatedList
            }

            val actualResult = sut(listOf(uiMuseumShop, uiShop), "", UiShop())
            val expectedResult = Either.Right(Unit)

            assert(actualResult == expectedResult)
        }
}