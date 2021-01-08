package com.example.crossingschedule.presentation.schedule.mapper

import com.example.crossingschedule.domain.core.Mapper
import com.example.crossingschedule.domain.model.Shop
import com.example.crossingschedule.presentation.schedule.model.KnownShops
import com.example.crossingschedule.presentation.schedule.model.UiShop
import javax.inject.Inject

class UiShopsToShopsMapper @Inject constructor(
) : Mapper<List<Shop>, List<UiShop>> {

    override fun map(origin: List<UiShop>): List<Shop> {
        return origin.map { uiShop ->
            when (uiShop.resourceImageId) {
                KnownShops.NooksCranny.resourceId -> Shop(
                    name = KnownShops.NooksCranny.shopName,
                    isVisited = uiShop.isVisited
                )
                KnownShops.AbleSisters.resourceId -> Shop(
                    name = KnownShops.AbleSisters.shopName,
                    isVisited = uiShop.isVisited
                )
                KnownShops.Museum.resourceId -> Shop(
                    name = KnownShops.Museum.shopName,
                    isVisited = uiShop.isVisited
                )
                else -> Shop(isVisited = uiShop.isVisited)
            }
        }
    }
}