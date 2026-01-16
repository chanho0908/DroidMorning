package com.peto.droidmorning.common.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime

object DateFormatter {
    private const val DATE_SEPARATOR = "."

    fun formatDate(
        instant: Instant,
        timeZone: TimeZone = TimeZone.currentSystemDefault(),
    ): String {
        val date =
            instant
                .toLocalDateTime(timeZone)
                .date

        return buildString {
            append(date.year)
            append(DATE_SEPARATOR)
            append(
                date.month.number
                    .toString()
                    .padStart(2, '0'),
            )
            append(DATE_SEPARATOR)
            append(date.day.toString().padStart(2, '0'))
        }
    }
}
