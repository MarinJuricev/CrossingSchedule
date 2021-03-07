package com.example.crossingschedule.feature.schedule.domain.usecase

import com.example.crossingschedule.core.model.Either
import com.example.crossingschedule.core.model.Failure
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
        currentPrices: UiTurnipPrices,
        currentDate: String,
        turnipPriceType: TurnipPriceType,
        updatedPrice: String,
    ): Either<Failure, Unit> {
        val updatesTurnipPrices = generateTurnipPrices(
            uiTurnipPricesToTurnipPricesMapper.map(currentPrices),
            turnipPriceType,
            updatedPrice
        )

        return repository.updateTurnipPrices(updatesTurnipPrices, currentDate)
    }

    private fun generateTurnipPrices(
        currentPrices: TurnipPrices,
        turnipPriceType: TurnipPriceType,
        updatedPrice: String
    ): TurnipPrices =
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
