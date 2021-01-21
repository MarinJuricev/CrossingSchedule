package com.example.crossingschedule.feature.schedule.presentation.model

import androidx.annotation.DrawableRes
import com.example.crossingschedule.R

enum class KnownShops(
    val shopName: String,
    @DrawableRes val resourceId: Int
) {
    NooksCranny("NooksCranny", R.drawable.nooks_cranny_shop_icon),
    AbleSisters("AbleSisters", R.drawable.able_sisters_shop_icon),
    Museum("Museum", R.drawable.museum_shop_icon)
}