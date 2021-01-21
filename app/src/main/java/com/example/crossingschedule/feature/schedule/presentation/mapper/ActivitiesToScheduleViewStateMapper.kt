package com.example.crossingschedule.feature.schedule.presentation.mapper

import android.text.format.DateFormat
import com.example.crossingschedule.core.util.Mapper
import com.example.crossingschedule.feature.schedule.domain.model.CrossingDailyActivities
import com.example.crossingschedule.feature.schedule.domain.model.Shop
import com.example.crossingschedule.feature.schedule.domain.model.TurnipPrices
import com.example.crossingschedule.feature.schedule.presentation.model.*
import java.util.*
import javax.inject.Inject

const val DESIRED_UI_FORMAT = "dd.MM.yyyy"

class ActivitiesToScheduleViewStateMapper @Inject constructor(
) : Mapper<ScheduleViewState, CrossingDailyActivities> {

    //TODO Separate into more independent mappers ?
    override fun map(origin: CrossingDailyActivities): ScheduleViewState {
        return with(origin) {
            ScheduleViewState(
                isLoading = false,
                errorMessage = "",
                dateOptions = mapDateToDateOptions(currentDate),
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

    private fun mapDateToDateOptions(date: Date): DateOptions {
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date

        return DateOptions(
            year = calendar.get(Calendar.YEAR),
            month = calendar.get(Calendar.MONTH),
            day = calendar.get(Calendar.DAY_OF_MONTH),
            formattedDate = DateFormat.format(DESIRED_UI_FORMAT, calendar).toString()
        )
    }

    private fun mapToUiTurnipPrices(turnipPrices: TurnipPrices): UiTurnipPrices =
        with(turnipPrices) {
            UiTurnipPrices(
                amPrice = amPrice.toString(),
                pmPrice = pmPrice.toString()
            )
        }
}