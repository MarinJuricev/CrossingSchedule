package com.example.crossingschedule.presentation.schedule.mapper

import android.text.format.DateFormat
import com.example.crossingschedule.domain.core.Mapper
import com.example.crossingschedule.domain.model.CrossingDailyActivities
import com.example.crossingschedule.domain.model.Shop
import com.example.crossingschedule.domain.model.TurnipPrices
import com.example.crossingschedule.presentation.schedule.model.KnownShops
import com.example.crossingschedule.presentation.schedule.model.ScheduleViewState
import com.example.crossingschedule.presentation.schedule.model.UiShop
import com.example.crossingschedule.presentation.schedule.model.UiTurnipPrices
import java.util.*
import javax.inject.Inject

const val DESIRED_UI_FORMAT = "dd.MM.yyyy"

class ActivitiesToScheduleViewStateMapper @Inject constructor(
) : Mapper<ScheduleViewState, CrossingDailyActivities> {

    override fun map(origin: CrossingDailyActivities): ScheduleViewState {
        return with(origin) {
            ScheduleViewState(
                isLoading = false,
                errorMessage = "",
                currentDate = mapDateToDesiredUIFormat(currentDate),
                shops = mapToUIShops(shops),
                crossingTodos = crossingTodos,
                notes = notes,
                turnipPrices = mapToUiTurnipPrices(turnipPrices),
                villagersInteraction = villagerInteractions
            )
        }
    }

    private fun mapToUIShops(shops: List<Shop>): List<UiShop> {
        return shops.map { shop ->
            when (shop.name) {
                KnownShops.NooksCranny.shopName -> UiShop(
                    resourceImageId = KnownShops.NooksCranny.resourceId,
                    isVisited = shop.isVisited
                )
                KnownShops.AbleSisters.shopName -> UiShop(
                    resourceImageId = KnownShops.AbleSisters.resourceId,
                    isVisited = shop.isVisited
                )
                KnownShops.Museum.shopName -> UiShop(
                    resourceImageId = KnownShops.Museum.resourceId,
                    isVisited = shop.isVisited
                )
                else -> UiShop(isVisited = shop.isVisited)
            }
        }
    }

    private fun mapDateToDesiredUIFormat(date: Date): String {
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date

        return DateFormat.format(DESIRED_UI_FORMAT, calendar).toString()
    }

    private fun mapToUiTurnipPrices(turnipPrices: TurnipPrices): UiTurnipPrices =
        with(turnipPrices) {
            UiTurnipPrices(
                amPrice = amPrice.toString(),
                pmPrice = amPrice.toString()
            )
        }
}