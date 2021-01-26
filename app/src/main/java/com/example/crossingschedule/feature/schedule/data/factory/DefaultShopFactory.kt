package com.example.crossingschedule.feature.schedule.data.factory

import com.example.crossingschedule.feature.schedule.domain.model.Shop

interface DefaultShopFactory {
    fun generate(): List<Shop>
}