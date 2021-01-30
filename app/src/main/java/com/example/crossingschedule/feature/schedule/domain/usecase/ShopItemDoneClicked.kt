package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.core.util.Either
import com.example.crossingschedule.core.util.Failure
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.schedule.domain.model.Shop
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import com.example.crossingschedule.feature.schedule.presentation.model.UiShop
import javax.inject.Inject

class ShopItemDoneClicked @Inject constructor(
    private val repository: ActivitiesRepository,
    private val uiShopListToShopListMapper: Mapper<List<Shop>, List<UiShop>>
) {

    suspend operator fun invoke(
        currentList: List<UiShop>,
        currentDate: String,
        clickedItem: UiShop
    ): Either<Failure, Unit> {
        //TODO add a ID field, don't just compare with the message
        val listToBeSent = generateListToBeSent(currentList, clickedItem)

        return repository.updateShopItems(
            uiShopListToShopListMapper.map(listToBeSent),
            currentDate
        )
    }

    private fun generateListToBeSent(
        currentList: List<UiShop>,
        clickedItem: UiShop
    ): List<UiShop> =
        if (currentList.isEmpty()) {
            listOf(clickedItem.copy(isVisited = !clickedItem.isVisited))
        } else {
            currentList.map { currentTodo ->
                if (currentTodo.resourceImageId == clickedItem.resourceImageId)
                    clickedItem.copy(isVisited = !clickedItem.isVisited)
                else
                    currentTodo
            }
        }
}