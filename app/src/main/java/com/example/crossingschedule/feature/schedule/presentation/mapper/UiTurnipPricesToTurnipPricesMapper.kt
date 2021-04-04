package com.example.crossingschedule.feature.schedule.presentation.mapper

import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.schedule.domain.model.TurnipPrices
import com.example.crossingschedule.feature.schedule.presentation.model.UiTurnipPrices
import javax.inject.Inject

class UiTurnipPricesToTurnipPricesMapper @Inject constructor(
) : Mapper<TurnipPrices, UiTurnipPrices> {

    override suspend fun map(origin: UiTurnipPrices): TurnipPrices {
        return with(origin) {
            TurnipPrices(
                amPrice = amPrice.toInt(),
                pmPrice = pmPrice.toInt()
            )
        }
    }
}