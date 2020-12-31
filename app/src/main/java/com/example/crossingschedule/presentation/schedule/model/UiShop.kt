package com.example.crossingschedule.presentation.schedule.model

import androidx.annotation.DrawableRes
import com.example.crossingschedule.R

data class UiShop(
    @DrawableRes val resourceImageId: Int = R.drawable.blank_shop_icon,
    val isVisited: Boolean = false
)