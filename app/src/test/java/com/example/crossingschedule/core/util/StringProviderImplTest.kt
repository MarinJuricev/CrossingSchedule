package com.example.crossingschedule.core.util

import android.content.Context
import com.example.crossingschedule.R
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

private const val ERROR_MESSAGE = "errorMessage"
private const val DATE = "15.12.1991"
private const val FORMATTED_DATE = "Date 15.12.1991"

class StringProviderImplTest {

    private val context: Context = mockk()

    private lateinit var sut: StringProvider

    @Before
    fun setUp() {
        sut = StringProviderImpl(
            context
        )
    }

    @Test
    fun `getString should return a string`() {
        every {
            context.getString(R.string.generic_error_message)
        } answers { ERROR_MESSAGE }

        val actualResult = sut.getString(R.string.generic_error_message)

        assertThat(actualResult).isEqualTo(ERROR_MESSAGE)
    }

    @Test
    fun `getFormattedString should return a formatted string`() {
        every {
            context.getString(R.string.current_date, DATE)
        } answers { FORMATTED_DATE }

        val actualResult = sut.getFormattedString(
            R.string.current_date,
            DATE
        )

        assertThat(actualResult).isEqualTo(FORMATTED_DATE)
    }
}