package com.example.crossingschedule.presentation.util

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

interface IStringProvider {
    fun getString(@StringRes stringResId: Int): String
    fun getFormattedString(@StringRes stringResId: Int, vararg formatArgs: Any?): String
}

class StringProviderImpl @Inject constructor(private var context: Context) : IStringProvider {

    override fun getString(@StringRes stringResId: Int): String {
        return context.getString(stringResId)
    }

    override fun getFormattedString(@StringRes stringResId: Int, vararg formatArgs: Any?): String {
        return context.getString(stringResId, *formatArgs)
    }
}