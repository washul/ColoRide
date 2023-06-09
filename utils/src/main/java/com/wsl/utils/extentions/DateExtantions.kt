package com.wsl.utils.extensions

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.showAsTitle(): String {
    return this.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM"))
}

fun LocalDate.showAsTitle(): String {
    return this.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM"))
}

fun LocalDateTime.showAsShortTitle(): String {
    return this.format(DateTimeFormatter.ofPattern("EEE dd MMMM"))
}

fun LocalTime.show12HoursFormatter(): String {
    return this.format(DateTimeFormatter.ofPattern("HH:mm a"))
}

fun LocalDateTime.showTodayTomorrow(): String {
    return when(true) {
        (this.dayOfYear == LocalDateTime.now().dayOfYear) -> "Today"
        (this.dayOfYear == LocalDateTime.now().plusDays(1).dayOfYear) -> "Tomorrow"
        else -> this.showAsShortTitle()
    }
}

fun LocalDate.appendTodayTomorrow(): String {
    return when(true) {
        (this.dayOfYear == LocalDateTime.now().dayOfYear) -> "Today ${this.showAsTitle()}"
        (this.dayOfYear == LocalDateTime.now().plusDays(1).dayOfYear) -> "Tomorrow, ${this.showAsTitle()}"
        else -> ""
    }
}

fun LocalDateTime.showHourAsString(): String {
    return this.format(DateTimeFormatter.ofPattern("HH:mm"))
}

/**
 * Compares the date between a range of two dates, with an hour of difference
 * */
fun LocalDateTime.isBetween(startDate: LocalDateTime, endDate: LocalDateTime): Boolean {
    return this.minusHours(1).isBefore(endDate) && this.plusHours(1).isAfter(startDate)
}