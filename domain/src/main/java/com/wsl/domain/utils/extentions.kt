package com.wsl.domain.utils

import com.google.gson.Gson
import com.wsl.domain.model.Auto
import com.wsl.domain.model.City
import com.wsl.domain.model.PlaceOfTheRoute
import com.wsl.domain.model.entities.auto.AutoEntity

fun String.fromJsonToCity(): City = Gson().fromJson(this, City::class.java)

fun City.fromCityToJson(): String = Gson().toJson(this)

fun PlaceOfTheRoute.toCustomString(): String {
    return when (this) {
        PlaceOfTheRoute.Arrival -> "Arrival"
        PlaceOfTheRoute.Departure -> "Departure"
        PlaceOfTheRoute.InTheMiddle((this as PlaceOfTheRoute.InTheMiddle).position) -> "$position"
        else -> {
            "noop"
        }
    }
}

fun String?.toPlaceOfTheRoute(): PlaceOfTheRoute? {
    if (this == null) return null

    return when (this) {
        "Arrival" -> PlaceOfTheRoute.Arrival
        "Departure" -> PlaceOfTheRoute.Departure
        else -> {
            val position = this.toInt()
            PlaceOfTheRoute.InTheMiddle(position)
        }
    }
}