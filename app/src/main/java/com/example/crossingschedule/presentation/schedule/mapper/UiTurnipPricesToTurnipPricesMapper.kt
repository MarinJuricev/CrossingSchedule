package com.example.crossingschedule.presentation.schedule.mapper

import com.example.crossingschedule.domain.core.Mapper
import com.example.crossingschedule.domain.model.TurnipPrices
import com.example.crossingschedule.presentation.schedule.model.UiTurnipPrices
import javax.inject.Inject

class UiTurnipPricesToTurnipPricesMapper @Inject constructor(
) : Mapper<TurnipPrices, UiTurnipPrices> {

    override fun map(origin: UiTurnipPrices): TurnipPrices {
        return with(origin) {
            TurnipPrices(
                amPrice = amPrice.toInt(),
                pmPrice = pmPrice.toInt()
            )
        }
    }
}