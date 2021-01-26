package com.example.crossingschedule.feature.schedule.data.factory

import com.example.crossingschedule.feature.schedule.domain.model.Shop
import javax.inject.Inject

class DefaultShopFactoryImpl @Inject constructor() : DefaultShopFactory {
    override fun generate(): List<Shop> =
        listOf(
            Shop("NooksCranny"),
            Shop("AbleSisters"),
            Shop("Museum")
        )
}