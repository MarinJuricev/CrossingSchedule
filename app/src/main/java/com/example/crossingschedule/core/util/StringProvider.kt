package com.example.crossingschedule.core.util

import androidx.annotation.StringRes

interface StringProvider {
    fun getString(@StringRes stringResId: Int): String
    fun getFormattedString(
        @StringRes stringResId: Int,
        vararg formatArgs: Any?,
    ): String
}