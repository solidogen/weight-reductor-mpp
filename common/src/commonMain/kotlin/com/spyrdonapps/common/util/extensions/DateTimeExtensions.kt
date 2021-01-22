package com.spyrdonapps.common.util.extensions

import com.spyrdonapps.common.util.utils.InstantStringFormat
import com.spyrdonapps.common.util.utils.InstantStringFormat.*
import com.spyrdonapps.common.util.utils.exhaustive
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Instant.getStringWithFormat(format: InstantStringFormat = dd_dot_MM_dot_yyyy): String {
    val localDateTime = this.toLocalDateTime(TimeZone.currentSystemDefault())
    with(localDateTime) {
        return when (format) {
            dd_dot_MM_dot_yyyy -> "$dayOfMonth.$monthNumberWithLeadingZero.$year"
        }.exhaustive
    }
}

val LocalDateTime.monthNumberWithLeadingZero: String
    get() = monthNumber.toString().padStart(2, '0')
