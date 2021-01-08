package com.example.crossingschedule.domain.usecase

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
        currentList: List<UiShop>,
        clickedItem: UiShop
    ) {
        //TODO add a ID field, don't just compare with the message
        val updatedList = currentList.map { currentTodo ->
            if (currentTodo.resourceImageId == clickedItem.resourceImageId)
                clickedItem.copy(isVisited = !clickedItem.isVisited)
            else
                currentTodo
        }

        repository.updateShopItems(uiShopListToShopListMapper.map(updatedList))
    }
}