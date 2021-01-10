package com.example.crossingschedule.domain.usecase.schedule

import com.example.crossingschedule.domain.core.Mapper
import com.example.crossingschedule.domain.model.Shop
import com.example.crossingschedule.domain.repository.ActivitiesRepository
import com.example.crossingschedule.presentation.schedule.model.UiShop
import javax.inject.Inject

class ShopItemDoneClicked @Inject constructor(
    private val repository: ActivitiesRepository,
    private val uiShopListToShopListMapper: Mapper<List<Shop>, List<UiShop>>
) {

    suspend operator fun invoke(
        currentList: List<UiShop>?,
        clickedItem: UiShop
    ) {
        //TODO add a ID field, don't just compare with the message
        val listToBeSent = generateListToBeSent(currentList, clickedItem)

        repository.updateShopItems(uiShopListToShopListMapper.map(listToBeSent))
    }

    private fun generateListToBeSent(
        currentList: List<UiShop>?,
        clickedItem: UiShop
    ): List<UiShop> =
        if (currentList.isNullOrEmpty()) {
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