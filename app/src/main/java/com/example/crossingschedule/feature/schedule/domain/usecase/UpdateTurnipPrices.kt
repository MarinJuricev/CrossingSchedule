package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.schedule.domain.model.TurnipPriceType
import com.example.crossingschedule.feature.schedule.domain.model.TurnipPrices
import com.example.crossingschedule.feature.schedule.domain.repository.ActivitiesRepository
import com.example.crossingschedule.feature.schedule.presentation.model.UiTurnipPrices
import javax.inject.Inject

class UpdateTurnipPrices @Inject constructor(
    private val repository: ActivitiesRepository,
    private val uiTurnipPricesToTurnipPricesMapper: Mapper<TurnipPrices, UiTurnipPrices>
) {

    suspend operator fun invoke(
        currentPrices: UiTurnipPrices?,
        turnipPriceType: TurnipPriceType,
        updatedPrice: String,
    ) {
        val turnipPrices = nullableMapToTurnipPrices(currentPrices)
        val updatesTurnipPrices = generateTurnipPrices(
            turnipPrices,
            turnipPriceType,
            updatedPrice
        )

        repository.updateTurnipPrices(updatesTurnipPrices)
    }

    private fun nullableMapToTurnipPrices(currentPrices: UiTurnipPrices?) =
        if (currentPrices != null) {
            uiTurnipPricesToTurnipPricesMapper.map(currentPrices)
        } else {
            null
        }

    private fun generateTurnipPrices(
        currentPrices: TurnipPrices?,
        turnipPriceType: TurnipPriceType,
        updatedPrice: String
    ): TurnipPrices =
        if (currentPrices == null) {
            buildFromNullablePrice(turnipPriceType, updatedPrice)
        } else {
            buildNonNullablePrice(currentPrices, turnipPriceType, updatedPrice)
        }

    private fun buildFromNullablePrice(
        turnipPriceType: TurnipPriceType,
        updatedPrice: String
    ) = if (turnipPriceType == TurnipPriceType.AM)
        TurnipPrices(amPrice = updatedPrice.toInt())
    else
        TurnipPrices(pmPrice = updatedPrice.toInt())

    private fun buildNonNullablePrice(
        currentPrices: TurnipPrices,
        turnipPriceType: TurnipPriceType,
        updatedPrice: String
    ) =
        if (turnipPriceType == TurnipPriceType.AM)
            TurnipPrices(
                amPrice = updatedPrice.toInt(),
                pmPrice = currentPrices.pmPrice
            )
        else
            TurnipPrices(
                amPrice = currentPrices.amPrice,
                pmPrice = updatedPrice.toInt()
            )

}