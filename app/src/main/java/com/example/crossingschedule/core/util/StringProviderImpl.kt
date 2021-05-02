package com.example.crossingschedule.core.util

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

class StringProviderImpl @Inject constructor(
    private val context: Context
) : StringProvider {

    override fun getString(@StringRes stringResId: Int): String {
        return context.getString(stringResId)
    }

    override fun getFormattedString(
        @StringRes stringResId: Int,
        vararg formatArgs: Any?,
    ): String {
        return context.getString(stringResId, *formatArgs)
    }
}