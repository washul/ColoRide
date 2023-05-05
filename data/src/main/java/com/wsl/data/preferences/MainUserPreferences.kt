package com.wsl.data.preferences

import android.content.Context
import com.wsl.domain.model.City
import com.wsl.domain.utils.fromCityToJson
import com.wsl.domain.utils.fromJsonToCity

const val USER_PREFERENCES = "USER_MAIN_PREFERENCES"
const val USER_DEPARTURE_PREFERENCE = "user_departure_preference"
const val USER_ARRIVAL_PREFERENCE = "user_arrival_preference"


class MainUserPreferences(context: Context) {

    private val preferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
    var cityDeparturePreference: City?
        get() = preferences.getString(USER_DEPARTURE_PREFERENCE, null)?.fromJsonToCity()
        set(value) {
            with(preferences.edit()) {
                this.putString(USER_DEPARTURE_PREFERENCE, value?.fromCityToJson()).apply()
            }
        }

    var cityArrivalPreference: City?
        get() = preferences.getString(USER_ARRIVAL_PREFERENCE, null)?.fromJsonToCity()
        set(value) {
            with(preferences.edit()) {
                this.putString(USER_ARRIVAL_PREFERENCE, value?.fromCityToJson()).apply()
            }
        }
}